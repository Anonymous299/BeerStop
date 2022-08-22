plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(libs.coreKTX)
    implementation(libs.compose)
    implementation(libs.composeMaterial)
    implementation(libs.composePreview)
    implementation(libs.lifecycleKTX)
    implementation(libs.composeActivity)
    testImplementation(libs.testJunit)
    androidTestImplementation(libs.testAndroidJunit)
    androidTestImplementation(libs.testAndroidEspresso)
    androidTestImplementation(libs.testAndroidCompose)
    debugImplementation(libs.composeTooling)
    debugImplementation(libs.debugComposeManifest)
}