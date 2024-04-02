// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.application.android.gradle)
        classpath(libs.application.detekt)
        classpath(libs.application.jacoco)
        classpath(libs.application.jacoco.agent)
        classpath(libs.application.jacoco.ant)
        classpath(libs.application.navigation.safe.args)
    }
}

apply(plugin = "io.gitlab.arturbosch.detekt")

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.detekt)
}
