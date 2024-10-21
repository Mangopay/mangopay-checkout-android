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
        maven {
            url = uri("https://nethone.jfrog.io/artifactory/nethonesdk-android-mangopay/")
        }

//        maven {
//            url = uri("https://oss.sonatype.org/content/repositories/commangopay-1306")
//        }
    }
}

rootProject.name = "Checkout"
include(":app")
 