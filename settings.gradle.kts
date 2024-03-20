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
            credentials {
                username = System.getenv("NETHONE_JFROG_CRED_USERNAME")
                password = System.getenv("NETHONE_JFROG_CRED_PASSWORD")
            }
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/commangopay-1243")
        }
    }
}

rootProject.name = "Checkout"
include(":app")
 