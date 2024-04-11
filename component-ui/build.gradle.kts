plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    kotlin("kapt")
}
apply("$rootDir/build.maven.gradle")
android {
    namespace = "androidx.core.extension.component.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = 21 }
    buildTypes { release { isMinifyEnabled = false } }
    buildFeatures.viewBinding = true
    buildFeatures.dataBinding = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    compileOnly(project(":extension"))
    implementation(libs.bundles.component.ui)
}