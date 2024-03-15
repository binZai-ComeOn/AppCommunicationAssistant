import com.binyouwei.buildsrc.DependenciesLibs
import com.binyouwei.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.binyouwei.androidserialport"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
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

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(DependenciesLibs.coreKtx)
}
