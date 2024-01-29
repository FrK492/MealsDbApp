plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.firebase-perf")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.farfun.mealz"
    compileSdk = rootProject.extra.get("compileSdkVersion") as Int

    defaultConfig {
        applicationId = "com.farfun.mealz"
        minSdk = rootProject.extra.get("minSdkVersion") as Int
        targetSdk = rootProject.extra.get("targetSdkVersion") as Int
        versionCode = 1
        versionName = "0.0.1"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra.get("kotlinCompilerExtensionVersion") as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core
    implementation("androidx.core:core-ktx:${rootProject.extra.get("ktxCoreVersion")}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra.get("appCompatVersion")}")
    // Compose
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("composeBomVersion")}"))
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-graphics")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:${rootProject.extra.get("activityComposeVersion")}")
    implementation("androidx.compose.runtime:runtime-livedata")
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra.get("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra.get("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${rootProject.extra.get("lifecycleVersion")}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra.get("lifecycleVersion")}")
    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("daggerHiltVersion")}")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    kapt("com.google.dagger:hilt-compiler:${rootProject.extra.get("daggerHiltVersion")}")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra.get("retrofitVersion")}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra.get("retrofitVersion")}")
    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra.get("roomVersion")}")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:${rootProject.extra.get("roomVersion")}")
    implementation("androidx.room:room-ktx:${rootProject.extra.get("roomVersion")}")
    implementation("androidx.room:room-paging:${rootProject.extra.get("roomVersion")}")
    // Navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra.get("navigationComposeVersion")}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra.get("hiltNavigationComposeVersion")}")
    // Coil
    implementation("io.coil-kt:coil-compose:${rootProject.extra.get("coilVersion")}")
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:${rootProject.extra.get("firebaseBomVesion")}"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-perf")
}
kapt {
    correctErrorTypes = true
}