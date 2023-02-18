buildscript {
    val kotlinVersion by extra { "1.6.10" }
    val navVersion by extra { "2.5.3" }

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")

        // Firebase
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
        classpath("com.google.firebase:perf-plugin:1.4.2")

        // OSS Licenses
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.6")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
