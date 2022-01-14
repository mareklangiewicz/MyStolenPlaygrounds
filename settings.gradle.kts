@file:Suppress("UnstableApiUsage")


pluginManagement {
    includeBuild("../deps.kt")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("pl.mareklangiewicz.deps.settings")
}

rootProject.name = "MyStolenPlaygrounds"
include (":app")
include(":lib1")
include(":lib-ui-samples")

