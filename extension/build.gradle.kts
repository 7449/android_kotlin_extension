plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    `maven-publish`
}
android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "androidx.core.extension"
    defaultConfig { minSdk = 21 }
    buildFeatures.viewBinding = true
    buildFeatures.dataBinding = true
    buildFeatures.compose = true
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}
dependencies {
    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.gson)
    implementation(kotlin("reflect"))
}
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components.getByName("release"))
                artifactId = extra["MAVEN_ARTIFACT_ID"].toString()
                groupId = extra["MAVEN_GROUP_ID"].toString()
                version = extra["MAVEN_VERSION"].toString()
            }
        }
    }
}