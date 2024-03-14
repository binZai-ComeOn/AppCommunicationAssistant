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
    implementation(DependenciesLibs.composeMaterial3)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    val composeBom = platform("androidx.compose:compose-bom:2024.02.02")
    implementation(composeBom)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // mqtt
    implementation(DependenciesLibs.mqttClient)
    implementation(DependenciesLibs.mqttService)
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}