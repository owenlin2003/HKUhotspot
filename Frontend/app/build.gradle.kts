plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.hku_hotspot.frontend"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hku_hotspot.frontend"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Use the same Compose version across all libraries
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // Optional logging interceptor


    // Maps SDK for Android
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation(kotlin("script-runtime"))

    //Heatmap
    implementation("com.google.maps.android:android-maps-utils:2.2.5")
    implementation("com.google.android.gms:play-services-maps:18.0.0")

    // Json

    implementation("com.beust:klaxon:5.5")
}



buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.android.tools.build:gradle:8.5.1")
    }
}

