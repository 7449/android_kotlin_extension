plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}
if (file("$rootDir/build.maven.gradle").exists()) {
    apply("$rootDir/build.maven.gradle")
}
android {
    namespace = "androidx.core.extension.http"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        consumerProguardFile(file("proguard-rules.pro"))
    }
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
    implementation(libs.bundles.http)
}