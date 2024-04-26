plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}
apply("$rootDir/build.maven.gradle")
android {
    namespace = "androidx.core.extension.flexbox"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
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
    implementation(libs.bundles.flexbox)
}