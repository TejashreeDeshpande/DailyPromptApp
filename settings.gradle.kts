pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DailyPromptApp"

include(":app")

// Feature modules — add new features here
include(":features:feature-search")
include(":features:feature-counter")
include(":features:feature-todolist")
include(":features:feature-chat-ui")
include(":features:feature-image-gallery")
include(":features:feature-quiz")
include(":features:feature-pagination")
include(":features:feature-leaderboard")
include(":features:feature-async-loader")
include(":features:feature-form-validation")
