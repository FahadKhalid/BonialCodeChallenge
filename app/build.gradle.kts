plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.app.bonialcodechallenge"
    compileSdk = 35

    buildFeatures {
        buildConfig = true // ✅ Enable BuildConfig fields
    }

    defaultConfig {
        applicationId = "com.app.bonialcodechallenge"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://mobile-s3-test-assets.aws-sdlc-bonial.com/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://mobile-s3-test-assets.aws-sdlc-bonial.com/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.gson)

    // Networking (Retrofit + Moshi)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.storage)
    ksp(libs.moshi.kotlin.codegen) // KSP instead of kapt

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Koin Core
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler) // For KSP processing
    // Koin for Jetpack Compose
    implementation(libs.koin.androidx.compose)

    // Coil (Image Loading)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}