plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
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

    implementation(libs.composeNavigation)
    implementation(libs.navigationGraph)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converterMoshi)
    implementation(libs.moshi)
    implementation(libs.coreKTX)
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    kapt(libs.moshiAP)
    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testAndroidJunit)
    androidTestImplementation(libs.testAndroidEspresso)
}