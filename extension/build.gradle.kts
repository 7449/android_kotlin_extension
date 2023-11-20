plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
android {
    compileSdk = libs.versions.compildSdk.get().toInt()
    namespace = "androidx.core.extension"
    defaultConfig { minSdk = 19 }
    buildFeatures.viewBinding = true
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    implementation(libs.bundles.androidx)
}