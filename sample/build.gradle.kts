plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
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
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    implementation(project(":extension"))
    implementation(project(":flexbox"))
    implementation(project(":http"))
    implementation(project(":component-ui"))
    implementation(project(":compose"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}
