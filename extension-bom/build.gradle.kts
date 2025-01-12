plugins {
    `java-platform`
    `maven-publish`
}
javaPlatform {
    allowDependencies()
}
dependencies {
    constraints {
        project.rootProject.subprojects.forEach { subproject ->
            if (subproject.properties["MAVEN_ARTIFACT_ID"] != null && subproject.name != "extension-bom") {
                api(subproject)
            }
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
            artifactId = properties["MAVEN_ARTIFACT_ID"].toString()
            groupId = rootProject.properties["MAVEN_GROUP_ID"].toString()
            version = rootProject.properties["MAVEN_VERSION"].toString()
        }
    }
}