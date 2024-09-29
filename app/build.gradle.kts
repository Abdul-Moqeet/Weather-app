plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.firstapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firstapp"
        minSdk = 24
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android libraries
    implementation(libs.androidx.core.ktx.v1120) // Replace with the latest version as needed
    implementation(libs.androidx.lifecycle.runtime.ktx.v261) // Align versions

    // Jetpack Compose dependencies
    implementation(libs.androidx.activity.compose.v172)
    implementation(libs.androidx.compose.bom.v20240400) // Check for the latest BOM version
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
//    implementation(libs.material3)




    // Lifecycle Compose integration
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1") // Use the latest version

    // Compose theme adapter
    implementation("com.google.android.material:compose-theme-adapter:1.2.1") // Latest version

    // Google Play Services for Auth (if needed)
    implementation("com.google.android.gms:play-services-auth:21.2.0") // Update to latest version if needed

    // LiveData for Compose
    implementation("androidx.compose.runtime:runtime-livedata:1.7.2") // Use the latest stable version

    // ViewModel integration for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6") // Use the latest stable version

    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // Retrofit for API integration
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Keep Retrofit and Converter versions aligned

    // OkHttp Logging Interceptor for debugging network requests
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation(libs.androidx.material3.android) // Use the latest stable version

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
