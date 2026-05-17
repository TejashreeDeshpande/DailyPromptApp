# DailyPromptApp

A multi-module Android project built as a personal testing playground.
The home screen lists all feature modules; tapping any card navigates into that feature with full back-stack support.

---

## Tech Stack

| Area | Detail |
|---|---|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose — BOM 2024.06.00 |
| Navigation | Navigation Compose 2.7.7 |
| State | `ViewModel` + `StateFlow` — one ViewModel per feature |
| Build | Gradle 8.6 (Kotlin DSL) · AGP 8.3.2 |
| Min SDK / Target SDK | 24 / 34 |

---

## Project Structure

```
DailyPromptApp/
├── gradle/
│   └── libs.versions.toml              # version catalog
├── app/                                # shell module
│   └── src/main/java/com/example/dailypromptapp/
│       ├── MainActivity.kt
│       ├── navigation/
│       │   └── AppNavHost.kt           # wires all feature nav graphs
│       └── ui/
│           ├── FeatureListScreen.kt
│           ├── FeatureRegistry.kt      # home screen feature list
│           └── theme/
├── features/
│   ├── feature-search/
│   │   └── src/main/java/com/example/feature/search/
│   │       ├── SearchScreen.kt
│   │       ├── viewmodel/
│   │       │   └── SearchViewModel.kt
│   │       └── navigation/
│   │           └── SearchNavigation.kt
│   └── feature-counter/
│       └── src/main/java/com/example/feature/counter/
│           ├── CounterScreen.kt
│           ├── model/
│           │   ├── Product.kt
│           │   └── ProductMockData.kt
│           ├── viewmodel/
│           │   └── CounterViewModel.kt
│           └── navigation/
│               └── CounterNavigation.kt
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

Each feature module is self-contained — Screen, ViewModel, Navigation, and optional Model.
Feature modules do **not** depend on `:app` or on each other.

---

## Module Dependency Graph

```
:app
 ├── :features:feature-search
 └── :features:feature-counter
```

---

## Existing Features

| Feature | Route | ViewModel | Description |
|---|---|---|---|
| Search | `search_screen` | `SearchViewModel` | Live-filter a country list by name or capital — `StateFlow` + `combine` |
| Counter | `counter_screen` | `CounterViewModel` | Per-product quantity counter with derived total — `StateFlow` + `map` |

---

## Adding a New Feature Module

Steps 1–4 are inside the new feature module. Steps 5–8 wire it into `:app`.

### 1. Create the module directory

```
features/feature-<name>/
├── build.gradle.kts
└── src/main/
    ├── AndroidManifest.xml
    └── java/com/example/feature/<name>/
        ├── <Name>Screen.kt
        ├── viewmodel/
        │   └── <Name>ViewModel.kt
        └── navigation/
            └── <Name>Navigation.kt
```

### 2. Add `build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.feature.<name>"
    compileSdk = 34
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.navigation.compose)
}
```

### 3. Create the ViewModel

State is exposed as `StateFlow`; the ViewModel is scoped to its composable via `viewModel()`.

```kotlin
class <Name>ViewModel : ViewModel() {
    private val _state = MutableStateFlow(<initialValue>)
    val state: StateFlow<<Type>> = _state

    fun onSomeAction() {
        // update _state
    }
}
```

### 4. Expose a navigation extension

```kotlin
const val ROUTE_<NAME> = "<name>_screen"

fun NavGraphBuilder.<name>Screen(navController: NavController) {
    composable(ROUTE_<NAME>) {
        <Name>Screen(onBack = { navController.popBackStack() })
    }
}
```

### 5. Register the module — `settings.gradle.kts`

```kotlin
include(":features:feature-<name>")
```

### 6. Add the dependency — `app/build.gradle.kts`

```kotlin
implementation(project(":features:feature-<name>"))
```

### 7. Wire navigation — `AppNavHost.kt`

```kotlin
NavHost(...) {
    composable(ROUTE_FEATURE_LIST) { FeatureListScreen(navController) }
    searchScreen(navController)
    counterScreen(navController)
    <name>Screen(navController)     // add this line
}
```

### 8. Add a home screen card — `FeatureRegistry.kt`

```kotlin
FeatureEntry(
    title = "<Name>",
    description = "Your feature description",
    icon = Icons.Default.<Icon>,
    route = ROUTE_<NAME>
)
```

---

## Practice Prompts

Interview-style briefs for ~40-minute coding sessions. Each prompt is one deliverable scoped to specific Kotlin / Compose / Android concepts.

### Easy — single screen, basic state

| Prompt | Key concepts |
|---|---|
| [Counter](prompts/feature-counter.md) | `remember`, `mutableStateOf`, button events |
| [To-do List](prompts/feature-todo-list.md) | `LazyColumn`, `mutableStateListOf`, toggle state |
| [Expense Tracker](prompts/feature-expense-tracker.md) | List mutation, derived total, delete item |
| [Search](prompts/feature-search.md) | `TextField`, live list filtering, clear-to-reset |
| [Favorites](prompts/feature-favorites.md) | Data class `copy()`, list filtering, toggle |
| [Sort & Filter](prompts/feature-sort-filter.md) | Multiple state transforms, `remember` chains |
| [Word Frequency](prompts/feature-word-frequency.md) | `groupBy`, `sortedByDescending`, string filtering, live update |
| [Leaderboard](prompts/feature-leaderboard.md) | Dense rank, `sortedByDescending`, `groupBy`, data class `copy()` |

### Medium — validation, async, multi-step logic

| Prompt | Key concepts |
|---|---|
| [Form Validation](prompts/feature-form-validation.md) | Real-time validation, `derivedStateOf`, input UX |
| [Chat UI](prompts/feature-chat-ui.md) | `LazyListState.scrollToItem`, message alignment, layout |
| [Quiz Flow](prompts/feature-quiz.md) | Sealed class state machine, `when` expression, multi-step UI |
| [Expandable List](prompts/feature-expandable-list.md) | `AnimatedVisibility`, exclusive expand state, `key` in lazy lists |
| [Pull to Refresh](prompts/feature-pull-to-refresh.md) | `PullRefreshIndicator`, coroutines, error handling |
| [Countdown Timer](prompts/feature-timer.md) | Coroutines, `LaunchedEffect`, pause / resume lifecycle |
| [Budget by Category](prompts/feature-budget-by-category.md) | `groupBy`, `sumOf`, sealed class categories, category filter |
| [Stopwatch + Laps](prompts/feature-stopwatch.md) | `Flow` ticker, 100 ms updates, `LaunchedEffect`, lap list |
| [Async Loader](prompts/feature-async-loader.md) | Sealed UI state (Loading / Success / Error), coroutines, retry |
| [Flow Combine — Search + Filter](prompts/feature-flow-combine.md) | `combine`, `StateFlow`, `debounce`, hot vs cold flows |
| [One-Time Events — SharedFlow](prompts/feature-shared-flow-events.md) | `SharedFlow`, one-shot UI events, `Snackbar`, `replay = 0` |

### Medium-Hard — navigation, persistence, animations

| Prompt | Key concepts |
|---|---|
| [Note Editor](prompts/feature-note-editor.md) | Navigation with arguments, `ViewModel` scoped to nav entry |
| [Tab Navigation](prompts/feature-tab-navigation.md) | `NavHost`, `BottomNavigation`, state restoration |
| [Animated Splash](prompts/feature-animated-splash.md) | `animate*AsState`, `NavHost` transitions, one-time launch |
| [Swipe to Delete](prompts/feature-swipe-to-delete.md) | `SwipeToDismiss`, `Snackbar`, undo pattern |
| [Image Gallery](prompts/feature-image-gallery.md) | `LazyVerticalGrid`, Coil image loading, async states |
| [Generic Selectable List](prompts/feature-generic-list.md) | Generic composable, type parameters, higher-order functions |
| [Cancellable Search — flatMapLatest](prompts/feature-flatmap-latest-search.md) | `flatMapLatest`, coroutine cancellation, `debounce`, loading states |
| [Stock Ticker — Flow Polling](prompts/feature-stock-ticker-polling.md) | `flow` builder, polling, `flatMapLatest` on pause, `conflate` |
| [Parallel Dashboard Loading](prompts/feature-parallel-loading-dashboard.md) | `async` / `await`, `supervisorScope`, per-section error + retry |
| [Download Queue — Channel](prompts/feature-download-queue-channel.md) | `Channel`, `consumeEach`, backpressure, sequential processing |

### Hard — persistence, system APIs, advanced patterns

| Prompt | Key concepts |
|---|---|
| [Settings Screen](prompts/feature-settings-screen.md) | `DataStore` / SharedPreferences, preference persistence |
| [Onboarding](prompts/feature-onboarding.md) | `HorizontalPager`, `DataStore`, one-time launch logic |
| [Offline Indicator](prompts/feature-offline-indicator.md) | `ConnectivityManager`, `Flow`, reactive UI state |
| [Pagination](prompts/feature-pagination.md) | Paging library, `collectAsLazyPagingItems`, loading / error states |
| [Notification Feed — Flow Operators](prompts/feature-notification-feed-operators.md) | `scan`, `conflate`, `buffer`, `distinctUntilChanged`, operator chaining |

---

## Running the Project

1. Open the `DailyPromptApp/` root folder in Android Studio Hedgehog or newer.
2. Wait for Gradle sync to finish.
3. Run on an emulator or device (API 24+).
