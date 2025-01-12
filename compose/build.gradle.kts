plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.compose.compiler)
}
if (file("$rootDir/build.maven.gradle").exists()) {
    apply("$rootDir/build.maven.gradle")
}
android {
    namespace = "androidx.core.extension.compose"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
    buildTypes { release { isMinifyEnabled = false } }
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
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}