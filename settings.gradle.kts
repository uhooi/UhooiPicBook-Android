pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }

    // ref: https://qiita.com/irgaly/items/45f84311f0c17a785644#新しい-plugin-dsl-に対応していない-plugin-の利用
    resolutionStrategy {
        eachPlugin {
            when(requested.id.id) {
                "com.google.android.gms.oss-licenses-plugin" -> useModule("com.google.android.gms:oss-licenses-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "UhooiPicBook"
include(":app")
