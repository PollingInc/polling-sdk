plugins {
    id("com.android.application")
}

android {
    namespace = "com.polling.sdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34  // Consider updating based on deprecation warning

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildToolsVersion = "34.0.0"
}

dependencies {
    // ... (dependencies remain unchanged)
}
