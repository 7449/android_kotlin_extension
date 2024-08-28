plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    kotlin("kapt")
}
apply("$rootDir/build.maven.gradle")
android {
    namespace = "androidx.core.extension.component.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
    buildTypes { release { isMinifyEnabled = false } }
    buildFeatures.viewBinding = true
    buildFeatures.dataBinding = true
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    compileOnly(project(":extension"))
    implementation(libs.bundles.component.ui)
}