plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // El plugin de Compose ya no necesita ser declarado aquí si usas buildFeatures
    id("com.google.gms.google-services")
}

android {
    namespace = "co.edu.unab.luisbohorquez.unabshop"
    compileSdk = 36

    defaultConfig {
        applicationId = "co.edu.unab.luisbohorquez.unabshop"
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
        // Para Firebase y Jetpack Compose moderno, es mejor usar Java 17 o superior.
        // Pero mantenemos 11 si es un requisito de tu configuración.
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    // Necesario para que Compose funcione correctamente
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Asegúrate de tener una versión compatible
    }
}

// --- SECCIÓN DE DEPENDENCIAS CORREGIDA ---
dependencies {

    // Dependencias Core y de Compose (las tenías bien)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // --- DEPENDENCIAS DE FIREBASE ---
    // 1. Plataforma (BOM) para gestionar versiones de Firebase (la tenías duplicada)
    // Se usa 'implementation(platform(...))' y se debe usar sin 'libs.' si no está en el catálogo.
    // Asumiendo que `libs.firebase.bom` está definido, lo dejamos.
    implementation(platform(libs.firebase.bom))

    // 2. Dependencias específicas de Firebase (ahora declaradas correctamente una sola vez)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics.ktx)

    // Dependencias de Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

