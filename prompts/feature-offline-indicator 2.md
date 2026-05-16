# Feature Prompt: Offline Indicator with callbackFlow

**Difficulty:** Hard  
**Time limit:** 40 minutes  
**Key concepts:** `callbackFlow`, `ConnectivityManager`, `NetworkCallback`, `awaitClose`, converting callback APIs to Flow

---

## Goal

Build a persistent connectivity banner that appears at the bottom of any screen when the device goes offline, and auto-dismisses when connectivity is restored.

---

## Requirements

### Functional
- Observe real network connectivity using `ConnectivityManager.NetworkCallback`
- Show a red "No internet connection" banner when offline
- Show a green "Back online" banner briefly (2 s) when connectivity returns, then hide it
- The banner survives configuration changes (ViewModel-owned flow)
- Works on API 24+

### UI
- The banner slides in from the bottom using `AnimatedVisibility`
- Red banner: "No internet connection" with a WiFi-off icon
- Green banner: "Back online" — auto-hides after 2 seconds
- The rest of the screen content is not covered (use `Scaffold` / `Column`)

---

## Repository / ViewModel API to implement

```kotlin
// In a repository or utility class:
fun observeConnectivity(context: Context): Flow<Boolean> = callbackFlow {
    val manager = context.getSystemService(ConnectivityManager::class.java)
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { trySend(true) }
        override fun onLost(network: Network)      { trySend(false) }
    }
    manager.registerDefaultNetworkCallback(callback)
    awaitClose { manager.unregisterNetworkCallback(callback) }
}

class ConnectivityViewModel(app: Application) : AndroidViewModel(app) {
    val isOnline: StateFlow<Boolean>
}
```

---

## Key implementation detail

```kotlin
// In ViewModel init:
isOnline = connectivityRepository
    .observeConnectivity(getApplication())
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)

// In composable — auto-hide the "Back online" banner:
val isOnline by viewModel.isOnline.collectAsState()
var showGreenBanner by remember { mutableStateOf(false) }

LaunchedEffect(isOnline) {
    if (isOnline) {
        showGreenBanner = true
        delay(2_000)
        showGreenBanner = false
    }
}
```

---

## Hints

- `callbackFlow` bridges a callback-based API into a `Flow` — the key pattern for SDK callbacks, sensors, BLE, etc.
- `trySend` is non-suspending; use it inside callbacks that aren't in coroutine context
- `awaitClose` is critical — it's called when the flow collector cancels, preventing memory leaks from unregistered callbacks
- Use `AndroidViewModel` to access `Application` context safely (avoids Activity context leaks)
- On the emulator, toggle airplane mode to test

---

## Stretch goals

- Wrap the flow in a `distinctUntilChanged()` to avoid duplicate emissions
- Expose `NetworkType` (WiFi / Mobile / None) instead of just a Boolean
- Add a `retry` button on the offline banner that attempts a real HEAD request to `https://www.google.com`
