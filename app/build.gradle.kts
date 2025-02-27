plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
}

android {
    namespace = "com.example.drinkinggameapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drinkinggameapp"
        minSdk = 26
        targetSdk = 35
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
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //coroutine
    runtimeOnly("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //immutable
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")

    //implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")

    //ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.7")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")

    //Material3
    implementation("androidx.compose.material3:material3:1.3.1")

//    //
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    androidTestImplementation("androidx.navigation:navigation-testing:2.7.4")
//    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//
//    //
//    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
//    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.runtime:runtime")
//    implementation("androidx.compose.runtime:runtime-livedata")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.core:core-ktx:1.12.0")
}