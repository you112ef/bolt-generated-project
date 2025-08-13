package com.aide.app.buildsystem.local

import com.aide.app.buildsystem.core.*

class LocalBuildEngine : BuildEngine {
	override suspend fun build(project: ProjectRef, mode: BuildMode): BuildResult {
		val logs = "Local build invoked for ${project.name} at ${project.path}"
		return BuildResult(success = true, logsPath = "", artifactPath = null)
	}

	override suspend fun sign(apkPath: String, keystore: KeystoreConfig): String {
		return apkPath // no-op placeholder
	}
}