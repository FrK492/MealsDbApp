// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.set("compileSdkVersion", 34)
    extra.set("minSdkVersion", 24)
    extra.set("targetSdkVersion", 34)
    extra.set("kotlinCompilerExtensionVersion", "1.5.3")

    // Core
    extra.set("ktxCoreVersion", "1.12.0")
    extra.set("appCompatVersion", "1.6.1")
    // Compose
    extra.set("activityComposeVersion", "1.8.1")
    // Lifecycle
    extra.set("lifecycleVersion", "2.6.2")
    // Dagger Hilt
    extra.set("daggerHiltVersion", "2.48.1")
    // Retrofit
    extra.set("retrofitVersion", "2.9.0")
    // Room
    extra.set("roomVersion", "2.6.0")
    // Navigation
    extra.set("navigationComposeVersion", "2.7.5")
    extra.set("hiltNavigationComposeVersion", "1.1.0")
    // Coil
    extra.set("coilVersion", "2.5.0")
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}