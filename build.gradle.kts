// Archivo: build.gradle.kts (A NIVEL DE PROYECTO)

plugins {
    // Declaramos el plugin de la aplicación Android
    id("com.android.application") version "8.5.1" apply false

    // Declaramos el plugin de Kotlin para Android
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false

    // Declaramos el plugin del Compilador de Compose
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false

    // Declaramos el plugin de Google Services para Firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
}

