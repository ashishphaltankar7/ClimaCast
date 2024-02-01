plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.climacast"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.climacast"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Code for enabling Binding in our project
    buildFeatures {
        viewBinding= true
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
}

dependencies {

    // dependency for enabling animated images in our activity
    implementation ("com.airbnb.android:lottie:6.1.0")
    //implementation ("com.airbnb.android:lottie:$lottieVersion")

    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // GSON converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("androidx.appcompat:appcompat:your_version_here")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.car.ui:car-ui-lib:2.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}