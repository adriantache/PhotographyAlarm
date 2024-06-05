// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kotlinVersion = "2.0.0"
    val kspVersion = "$kotlinVersion-1.0.21"

    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.google.devtools.ksp") version kspVersion apply false
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.compose") version kotlinVersion
}
