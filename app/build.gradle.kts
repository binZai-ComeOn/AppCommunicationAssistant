import com.binyouwei.buildsrc.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.binyouwei.appcommunicationassistant"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.binyouwei.appcommunicationassistant"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(DependenciesLibs.coreKtx)
    implementation(DependenciesLibs.appcompat)
    implementation(DependenciesLibs.material)

    implementation(DependenciesLibs.liveRuntimeKtx)
    implementation(DependenciesLibs.activityCompose)
    implementation(platform(DependenciesLibs.composeBom))
    implementation(DependenciesLibs.composeUi)
    implementation(DependenciesLibs.composeUiGraphics)
    implementation(DependenciesLibs.composeUiToolingPreview)
    implementation(DependenciesLibs.composeMaterial)
    implementation(DependenciesLibs.composeMaterial3)
    implementation(DependenciesLibs.constraintlayoutCompose)
    androidTestImplementation(platform(DependenciesLibs.constraintlayoutCompose))
    androidTestImplementation(DependenciesLibs.composeUiTtestJunit4)

    debugImplementation(DependenciesLibs.composeUiToolingPreview)
    implementation(DependenciesLibs.composeUiToolingPreview)
    debugImplementation(DependenciesLibs.composeUiTestManifest)

    // mqtt
    implementation(DependenciesLibs.mqttClient)
    implementation(DependenciesLibs.mqttService)

    implementation(project(path = ":lib_serialport"))
}