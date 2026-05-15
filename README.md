# DailyPromptApp

A multi-module Android project built as a personal testing playground. The app displays a list of feature modules on the home screen and navigates into each feature screen with full back-stack support.

---

## Tech Stack

| Layer | Library / Tool |
|---|---|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose |
| Navigation | Navigation Compose 2.7.7 |
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
│   └── feature-search/                 # Search feature module
│       └── src/main/
│           └── java/com/example/feature/search/
│               ├── SearchScreen.kt
│               └── navigation/
│                   └── SearchNavigation.kt
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## Module Dependency Graph

```
:app
 └── :features:feature-search
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

### 3. Expose a navigation extension

```kotlin
// features/feature-<name>/src/.../navigation/<Name>Navigation.kt

const val ROUTE_<NAME> = "<name>_screen"

fun NavGraphBuilder.<name>Screen(navController: NavController) {
    composable(ROUTE_<NAME>) {
        <Name>Screen(onBack = { navController.popBackStack() })
    }
}
```

### 4. Register the module in `settings.gradle.kts`

```kotlin
include(":features:feature-<name>")
```

### 5. Add the dependency in `app/build.gradle.kts`

```kotlin
implementation(project(":features:feature-<name>"))
```

### 6. Wire navigation in `AppNavHost.kt`

```kotlin
NavHost(...) {
    composable(ROUTE_FEATURE_LIST) { FeatureListScreen(navController) }
    searchScreen(navController)
    <name>Screen(navController)   // add this line
}
```

### 7. Add a card to the home list in `FeatureRegistry.kt`

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

| Feature | Route | Description |
|---|---|---|
| Search | `search_screen` | Live-filter search over a list of sample daily prompts |
# DailyPromptApp
