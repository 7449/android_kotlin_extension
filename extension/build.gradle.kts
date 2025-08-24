plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}
apply("$rootDir/build.maven.gradle")
android {
    namespace = "androidx.core.extension"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
    buildFeatures.viewBinding = true
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
}
dependencies {
    implementation(libs.bundles.extension)
    implementation(kotlin("reflect"))
}