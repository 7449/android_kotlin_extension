plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.compose.compiler) apply false
}

tasks.register("publishMavenAll") {
    group = "publishing"
    fun projectIsMavenTask(project: Project): Boolean {
        return runCatching { project.tasks.named("publishToMavenLocal") }.isSuccess
    }

    fun queryAllMavenProject(project: Project): Array<Task> {
        return if (project.childProjects.isEmpty() && projectIsMavenTask(project))
            arrayOf(project.tasks.named("publishToMavenLocal").get())
        else project.childProjects
            .flatMap { queryAllMavenProject(it.value).toList() }
            .toTypedArray()
    }

    val mavenProject = queryAllMavenProject(project)
    dependsOn(*mavenProject)
}