# DailyPromptApp

A multi-module Android project built as a personal testing playground. The app displays a list of feature modules on the home screen and navigates into each feature screen with full back-stack support.

---

## Tech Stack

| Layer | Library / Tool |
|---|---|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose |
| Navigation | Navigation Compose 2.7.7 |
| State management | `ViewModel` + `StateFlow` (one ViewModel per feature) |
| Build system | Gradle 8.6 (Kotlin DSL) |
| Android Gradle Plugin | 8.3.2 |
| Compose BOM | 2024.06.00 |
| Min SDK | 24 |
| Target SDK | 34 |

---

## Project Structure

```
DailyPromptApp/
├── gradle/
│   └── libs.versions.toml              # Centralized version catalog
├── app/                                # Main application module
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/example/dailypromptapp/
│           ├── MainActivity.kt
│           ├── navigation/
│           │   └── AppNavHost.kt       # Root NavHost, wires all feature graphs
│           └── ui/
│               ├── FeatureListScreen.kt
│               ├── FeatureRegistry.kt  # Central list of features shown on home
│               └── theme/
├── features/
│   ├── feature-search/                 # Search feature module
│   │   └── src/main/
│   │       └── java/com/example/feature/search/
│   │           ├── SearchScreen.kt
│   │           ├── viewmodel/
│   │           │   └── SearchViewModel.kt
│   │           └── navigation/
│   │               └── SearchNavigation.kt
│   └── feature-counter/                # Counter feature module
│       └── src/main/
│           └── java/com/example/feature/counter/
│               ├── CounterScreen.kt
│               ├── model/
│               │   ├── Product.kt
│               │   └── ProductMockData.kt
│               ├── viewmodel/
│               │   └── CounterViewModel.kt
│               └── navigation/
│                   └── CounterNavigation.kt
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## Module Dependency Graph

```
:app
 ├── :features:feature-search
 └── :features:feature-counter
```

The `:app` module depends on all feature modules. Feature modules are standalone libraries — they do not depend on `:app` or on each other.

---

## How Navigation Works

- `MainActivity` hosts a single `AppNavHost` composable.
- `AppNavHost` uses a `NavHost` with `feature_list` as the start destination.
- Each feature module exposes a `NavGraphBuilder` extension function (e.g. `searchScreen()`).
- `FeatureListScreen` reads from `featureRegistry` and renders a tappable card per feature.
- Tapping a card calls `navController.navigate(feature.route)`.
- Each feature screen has a back arrow that calls `navController.popBackStack()`.

---

## Adding a New Feature Module

### 1. Create the module directory

```
features/
└── feature-<name>/
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

### 2. `build.gradle.kts` (copy from `feature-search`)

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

Each feature owns its own `ViewModel` scoped to its composable via `viewModel()`. State is exposed as `StateFlow`.

```kotlin
// features/feature-<name>/src/.../viewmodel/<Name>ViewModel.kt

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
// features/feature-<name>/src/.../navigation/<Name>Navigation.kt

const val ROUTE_<NAME> = "<name>_screen"

fun NavGraphBuilder.<name>Screen(navController: NavController) {
    composable(ROUTE_<NAME>) {
        <Name>Screen(onBack = { navController.popBackStack() })
    }
}
```

### 5. Register the module in `settings.gradle.kts`

```kotlin
include(":features:feature-<name>")
```

### 6. Add the dependency in `app/build.gradle.kts`

```kotlin
implementation(project(":features:feature-<name>"))
```

### 7. Wire navigation in `AppNavHost.kt`

```kotlin
NavHost(...) {
    composable(ROUTE_FEATURE_LIST) { FeatureListScreen(navController) }
    searchScreen(navController)
    <name>Screen(navController)   // add this line
}
```

### 8. Add a card to the home list in `FeatureRegistry.kt`

```kotlin
val featureRegistry: List<FeatureEntry> = listOf(
    FeatureEntry(
        title = "Search",
        description = "Search for prompts and content",
        icon = Icons.Default.Search,
        route = ROUTE_SEARCH
    ),
    FeatureEntry(
        title = "<Name>",
        description = "Your feature description",
        icon = Icons.Default.<Icon>,
        route = ROUTE_<NAME>
    )
)
```

---

## Running the Project

1. Open the root `DailyPromptApp/` folder in Android Studio (Hedgehog or newer).
2. Wait for Gradle sync to complete.
3. Run on an emulator or physical device (API 24+).

---

## Existing Features

| Feature | Route | ViewModel | Description |
|---|---|---|---|
| Search | `search_screen` | `SearchViewModel` | Live-filter search over a list of countries using `StateFlow` + `combine` |
| Counter | `counter_screen` | `CounterViewModel` | Per-product quantity counter with a derived total using `StateFlow` + `map` |

---

## Practice Prompts

Interview-style feature prompts for 40-minute coding sessions. Each prompt is scoped to one deliverable and targets specific Android/Kotlin/Compose fundamentals. Ordered from easiest to hardest.

### Easy — single screen, basic state

| Prompt file | Key concepts |
|---|---|
| [feature-counter.md](prompts/feature-counter.md) | `remember`, `mutableStateOf`, simple UI events |
| [feature-todo-list.md](prompts/feature-todo-list.md) | `LazyColumn`, `mutableStateListOf`, toggle state |
| [feature-expense-tracker.md](prompts/feature-expense-tracker.md) | List mutation, derived total, delete item |
| [feature-search.md](prompts/feature-search.md) | `TextField`, live list filtering, clear-to-reset |
| [feature-favorites.md](prompts/feature-favorites.md) | Data class `copy()`, list filtering, toggle |
| [feature-sort-filter.md](prompts/feature-sort-filter.md) | Composing multiple state transforms, `remember` chains |

### Medium — validation, async, multi-step logic

| Prompt file | Key concepts |
|---|---|
| [feature-form-validation.md](prompts/feature-form-validation.md) | Real-time validation, `derivedStateOf`, input UX |
| [feature-chat-ui.md](prompts/feature-chat-ui.md) | `LazyListState.scrollToItem`, message alignment, layout |
| [feature-quiz.md](prompts/feature-quiz.md) | State machines, sealed classes, multi-step UI flows |
| [feature-expandable-list.md](prompts/feature-expandable-list.md) | `AnimatedVisibility`, exclusive state, `key` in lazy lists |
| [feature-pull-to-refresh.md](prompts/feature-pull-to-refresh.md) | `PullRefreshIndicator`, coroutines, error handling |
| [feature-timer.md](prompts/feature-timer.md) | Coroutines, `LaunchedEffect`, pause/resume lifecycle |

### Medium-Hard — navigation, persistence, animations

| Prompt file | Key concepts |
|---|---|
| [feature-note-editor.md](prompts/feature-note-editor.md) | Navigation with arguments, `ViewModel` scoped to nav entry |
| [feature-tab-navigation.md](prompts/feature-tab-navigation.md) | `NavHost`, `BottomNavigation`, state restoration |
| [feature-animated-splash.md](prompts/feature-animated-splash.md) | `animate*AsState`, `NavHost` transitions, launch logic |
| [feature-swipe-to-delete.md](prompts/feature-swipe-to-delete.md) | `SwipeToDismiss`, `Snackbar`, undo pattern |
| [feature-image-gallery.md](prompts/feature-image-gallery.md) | `LazyVerticalGrid`, Coil/image loading, async states |

### Hard — persistence, system APIs, advanced patterns

| Prompt file | Key concepts |
|---|---|
| [feature-settings-screen.md](prompts/feature-settings-screen.md) | `DataStore`/SharedPreferences, preference persistence |
| [feature-onboarding.md](prompts/feature-onboarding.md) | `HorizontalPager`, `DataStore`, one-time launch logic |
| [feature-offline-indicator.md](prompts/feature-offline-indicator.md) | `ConnectivityManager`, `Flow`, reactive UI state |
| [feature-pagination.md](prompts/feature-pagination.md) | Paging, `collectAsLazyPagingItems`, loading/error states |
