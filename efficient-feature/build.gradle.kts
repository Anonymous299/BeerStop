plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(libs.compose)
    implementation(libs.composeMaterial)
    implementation(libs.composePreview)
    implementation(libs.lifecycleKTX)
    implementation(libs.composeActivity)
    implementation(libs.composeNavigation)
    implementation(libs.hilt)
    implementation(libs.navigationGraph)
    implementation(libs.room)
    implementation(libs.gson)
    implementation(libs.converterMoshi)
    implementation(libs.moshi)
    implementation(libs.roomKtx)
    implementation(libs.composeAccompanistSwipeRefresh)
    kapt(libs.roomAP)
    kapt(libs.hiltCompiler)
    kapt(libs.navigationGraphAP)
    kapt(libs.moshiAP)
    implementation(libs.retrofit)
    implementation(libs.hiltNavigation)
    implementation(libs.coreKTX)
    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testAndroidJunit)
    androidTestImplementation(libs.testAndroidEspresso)
    androidTestImplementation(libs.testAndroidCompose)
    debugImplementation(libs.debugComposeTooling)
    debugImplementation(libs.debugComposeManifest)
}