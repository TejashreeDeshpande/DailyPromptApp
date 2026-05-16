# Feature Prompt: One-Time Events with SharedFlow

**Difficulty:** Medium  
**Time limit:** 40 minutes  
**Key concepts:** `SharedFlow`, `MutableSharedFlow`, one-shot UI events, `Snackbar`, avoiding `StateFlow` for navigation/toasts

---

## Goal

Build a "Submit Feedback" form screen that sends one-time events (show Snackbar, navigate away) from the ViewModel to the UI without leaking them on recomposition.

---

## Requirements

### Functional
- A form with two fields: **Name** (required) and **Message** (required, min 10 chars)
- A **Submit** button — disabled while fields are invalid
- On submit:
  - Show a `Snackbar` with "Feedback sent!" (one-time, not re-shown on rotation)
  - Clear the form fields
- If submission "fails" (simulate with a 30% random chance), show a `Snackbar` with "Something went wrong. Try again."

### UI
- Two `OutlinedTextField`s with inline error labels
- Submit button at the bottom
- `ScaffoldState` / `SnackbarHostState` wired to the ViewModel events

---

## ViewModel API to implement

```kotlin
sealed class FeedbackEvent {
    data object Success : FeedbackEvent()
    data class Error(val message: String) : FeedbackEvent()
}

class FeedbackViewModel : ViewModel() {
    val name: StateFlow<String>
    val message: StateFlow<String>
    val isSubmitEnabled: StateFlow<Boolean>
    val events: SharedFlow<FeedbackEvent>   // replay = 0

    fun onNameChange(v: String)
    fun onMessageChange(v: String)
    fun onSubmit()
}
```

---

## Key implementation detail

```kotlin
private val _events = MutableSharedFlow<FeedbackEvent>()   // replay = 0 is the default
val events: SharedFlow<FeedbackEvent> = _events

// In the composable:
LaunchedEffect(Unit) {
    viewModel.events.collect { event ->
        when (event) {
            is FeedbackEvent.Success -> snackbarHostState.showSnackbar("Feedback sent!")
            is FeedbackEvent.Error   -> snackbarHostState.showSnackbar(event.message)
        }
    }
}
```

---

## Hints

- **Why not `StateFlow`?** `StateFlow` replays the last value to new collectors — a new subscriber after rotation would re-show the Snackbar. `SharedFlow(replay=0)` fires once and is gone.
- `MutableSharedFlow` is a hot flow; no items are buffered by default
- Use `viewModelScope.launch { _events.emit(...) }` — `emit` is a suspend function
- `isSubmitEnabled` can be a `combine` of `name` and `message` flows

---

## Stretch goals

- Add a "Navigate to confirmation screen" event type and handle it in `LaunchedEffect`
- Debounce the submit button to prevent double-taps
- Write a unit test that asserts the correct event is emitted on success vs failure
