

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // Usar 1.8 es más estándar
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }



    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

// Archivo: app/build.gradle.kts
// ¡REEMPLAZA COMPLETAMENTE TU BLOQUE DE DEPENDENCIAS CON ESTE!

dependencies {

    // --- VERSIONES ESTABLES Y COMPATIBLES ---

    // Dependencias de AndroidX
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3") // Versión estable compatible
    implementation("androidx.activity:activity-compose:1.9.0")

    // BOM de Compose (Bill of Materials) - Usamos la versión estable de Junio 2024
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00") // ¡CLAVE!
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Dependencias de la UI de Compose (ya no necesitan versión gracias al BOM)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Dependencias de Firebase
    // BOM de Firebase para gestionar sus versiones
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Dependencia de Credenciales y Google ID
    implementation("androidx.credentials:credentials:1.3.0-alpha02")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha02")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    // Dependencia para el .await() de las corrutinas de Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1-Beta")

    // Dependencias de Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
