package com.binyouwei.buildsrc

object Versions {
    // 使用exoPlayer编译版本需要到34，而34中有bug,xml写代码没有代码提示
    const val compileSdk = 34
    const val buildToolsVersion = "32.0.0"
    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object DependenciesLibs {

    const val coreKtx = "androidx.core:core-ktx:+"
    const val kotlinBom = "org.jetbrains.kotlin:kotlin-bom:1.8.0"
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val material = "com.google.android.material:material:1.8.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val constraintlayoutCompose = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
    const val liveRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    const val activityCompose = "androidx.activity:activity-compose:1.7.0"

    const val composeBom = "androidx.compose:compose-bom:2024.02.02"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiTtestJunit4 = "androidx.compose.ui:ui-test-junit4"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial = "androidx.compose.material:material:1.6.4"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"

    // mqtt
    const val mqttClient = "org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.4"
    const val mqttService = "org.eclipse.paho:org.eclipse.paho.android.service:1.1.1"

}
