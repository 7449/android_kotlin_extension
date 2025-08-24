plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.serialization)
}
android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "androidx.core.extension.sample"
    defaultConfig {
        targetSdk = libs.versions.targetSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    buildFeatures.viewBinding = true
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvmTarget.get())
    }
}
dependencies {
    implementation(kotlin("reflect"))
    implementation(project(":extension"))
    implementation(project(":compose"))
    implementation(project(":compose-material"))
    implementation(project(":compose-material3"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.sample)
    implementation(libs.bundles.compose.material)
    implementation(libs.bundles.compose.material3)
    implementation(libs.bundles.compose.material3)
}
