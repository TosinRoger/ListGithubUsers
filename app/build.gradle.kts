plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("io.gitlab.arturbosch.detekt")
    id("jacoco")
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
            )
        )
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.cardview)
    implementation(libs.constraint.layout)
    implementation(libs.material)

    // Internet connection
//    implementation(libs.gson)
//    implementation(libs.json)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.coroutines.adapter)


    testImplementation(libs.junit)
    // Internet connection
    testImplementation(libs.test.retrofit.mock)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
