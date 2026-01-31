import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.secrets.gradle)
}

secrets {
    propertiesFileName = "local.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("sdk.dir")
}

android {
    namespace = "com.ale.nutricheck"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ale.nutricheck"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }



    buildFeatures {
        compose = true
        buildConfig = true
        resValues = true
    }



    flavorDimensions.add("environment")
    productFlavors {
        create("dev") {
            dimension = "environment"
            // URL de prueba (Open Food Facts)
            buildConfigField("String", "BASE_URL", "\"https://world.openfoodfacts.org/\"")
            resValue("string", "app_name", "NutriCheck (DEV)")
        }

        create("prod") {
            dimension = "environment"
            // URL de producción
            buildConfigField("String", "BASE_URL", "\"https://world.openfoodfacts.org/\"")
            resValue("string", "app_name", "NutriCheck")
        }
    }

}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)   //viewModel()
    implementation(libs.com.squareup.retrofit2.retrofit)        // Retrofit
    implementation(libs.com.squareup.retrofit2.converter.json)  // JSON
    implementation(libs.io.coil.kt.coil.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui)                // IO
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}