plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.android.gms.oss-licenses-plugin")
    id("io.gitlab.arturbosch.detekt") version "1.17.0"
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.theuhooi.uhooipicbook"
        minSdk = 26
        targetSdk = 31
        versionCode = 7
        versionName = "1.6.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // For LeakCanary in instrumentation tests
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner" // TODO: Remove
        testInstrumentationRunnerArguments += mapOf("listener" to "leakcanary.FailTestOnLeakRunListener")
    }

    signingConfigs {
        create("release") {
            storeFile = file("release.keystore")
            storePassword = System.getenv("RELEASE_KEYSTORE_STORE_PASSWORD")
            keyAlias = "uhooipicbook"
            keyPassword = System.getenv("RELEASE_KEYSTORE_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        dataBinding = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("production") {
            dimension = "environment"
        }
        create("develop") {
            dimension = "environment"
            applicationIdSuffix = ".develop"
            versionNameSuffix = "-develop"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra["navVersion"]}") // TODO: Remove because unused
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra["navVersion"]}")

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    val coroutinesVersion = "1.6.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.3"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-perf")

    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    val coilVersion = "1.4.0"
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-base:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")

    testImplementation("org.robolectric:robolectric:4.6.1")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test.ext:junit:1.1.3")

    // LeakCanary
    val leakcanaryVersion = "2.7"
    debugImplementation("com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion")
    androidTestImplementation("com.squareup.leakcanary:leakcanary-android-instrumentation:$leakcanaryVersion")
}

kapt {
    correctErrorTypes = true
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$projectDir/config/detekt/detekt.yml")
    baseline = file("$projectDir/config/detekt/baseline.xml")
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.html")
        }
        txt {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.txt")
        }
        sarif {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.sarif")
        }
    }
}

// Workaround for using the enableAggregatingTask flag.
// ref: https://github.com/google/dagger/issues/2744#issuecomment-901277926
tasks.named("getDependencies").configure {
    var configured = false
    project.android.applicationVariants.all {
        if (!configured) {
            inputs.files(project.files(project.configurations.getByName("productionDebugAndroidTestRuntimeClasspath")))
            configured = true
        }
    }
}
