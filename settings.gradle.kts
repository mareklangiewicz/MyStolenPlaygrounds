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
include(":playgrounds-app")
include(":playgrounds-basic")
include(":playgrounds-ui-samples")
include(":playgrounds-material3")

