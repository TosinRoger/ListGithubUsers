plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.devtools.ksp")
    id("io.gitlab.arturbosch.detekt")
    id("jacoco")
    id("androidx.navigation.safeargs")
}

apply {
    from("../tools/jacoco.gradle")
//    from("../tools/detekt/detekt.gradle")
}


android {
    namespace = "br.com.tosin.listgithubusers"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.tosin.listgithubusers"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions.apply {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    sourceSets.getByName("main") {
        res.srcDirs(
            listOf(
                "src/main/res",
                "src/main/res/layouts",
                "src/main/res/layouts/activity",
                "src/main/res/layouts/components",
                "src/main/res/layouts/user/detail",
                "src/main/res/layouts/user/list",
            )
        )
    }
}

dependencies {
    // LIBs
    implementation(libs.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.cardview)
    implementation(libs.constraint.layout)
    implementation(libs.material)
    // Navigation
    implementation(libs.navigation.dynamic.features.fragment)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    // Paging
    implementation(libs.paging.runtime.ktx)
    // Glide
    implementation(libs.glide)
    ksp(libs.glide.ksp)
    // Internet connection
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.coroutines.adapter)

    // UNIT TEST
    testImplementation(libs.junit)
    testImplementation(libs.test.android.arch)
    testImplementation(libs.test.androidx.core)
    testImplementation(libs.test.navigation)
    testImplementation(libs.test.kotlinx.coroutines)
    testImplementation(libs.test.kotlin.junit)
    testImplementation(libs.test.retrofit.mock)
    testImplementation(libs.test.mockk)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
