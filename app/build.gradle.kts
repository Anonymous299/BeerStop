plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.trype.beerstop"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(mapOf("path" to ":core")))
    implementation(libs.coreKTX)
    implementation(libs.compose)
    implementation(libs.composeMaterial)
    implementation(libs.composePreview)
    implementation(libs.lifecycleKTX)
    implementation(libs.composeActivity)
    implementation(libs.composeNavigation)
    implementation(libs.navigationGraph)
    implementation(libs.hilt)
    implementation(libs.retrofit)
    implementation(project(mapOf("path" to ":home")))
    implementation(project(mapOf("path" to ":search")))
    kapt(libs.hiltCompiler)
    annotationProcessor(libs.navigationGraphAP)
    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testAndroidJunit)
    androidTestImplementation(libs.testAndroidEspresso)
    androidTestImplementation(libs.testAndroidCompose)
    debugImplementation(libs.debugComposeTooling)
    debugImplementation(libs.debugComposeManifest)
}