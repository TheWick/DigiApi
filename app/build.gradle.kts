plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.digiapi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.digiapi"
        minSdk = 26
        targetSdk = 36
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

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    @Suppress("DEPRECATION")
    sourceSets["main"].java.srcDir("build/generated/ksp/main/kotlin")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Core Android
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxLifecycleRuntimeKtx)
    implementation(libs.androidxActivityCompose)

    // Compose
    implementation(platform(libs.androidxComposeBom))
    implementation(libs.androidxComposeUi)
    implementation(libs.androidxComposeUiGraphics)
    implementation(libs.androidxComposeUiToolingPreview)
    implementation(libs.androidxComposeMaterial3)
    implementation(libs.androidxComposeMaterialIconsExtended)

    // Navigation
    implementation(libs.androidxNavigationCompose)
    implementation(libs.androidxHiltNavigationCompose)

    // ViewModel
    implementation(libs.androidxLifecycleViewmodelCompose)
    implementation(libs.androidxLifecycleRuntimeCompose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.okhttpLoggingInterceptor)

    // Coil (Images)
    implementation(libs.coilCompose)

    // Hilt (DI)
    implementation(libs.hiltAndroid)
    ksp(libs.hiltCompiler)

    // Coroutines
    implementation(libs.kotlinxCoroutinesAndroid)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.androidxEspressoCore)
    androidTestImplementation(platform(libs.androidxComposeBom))
    androidTestImplementation(libs.androidxComposeUiTestJunit4)
    debugImplementation(libs.androidxComposeUiTooling)
    debugImplementation(libs.androidxComposeUiTestManifest)
}