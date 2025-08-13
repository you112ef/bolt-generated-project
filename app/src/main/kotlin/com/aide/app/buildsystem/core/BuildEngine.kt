package com.aide.app.buildsystem.core

sealed interface BuildMode { data object Local: BuildMode; data object Cloud: BuildMode }

data class ProjectRef(val id: Long, val path: String, val name: String)

data class BuildResult(val success: Boolean, val logsPath: String, val artifactPath: String?)

data class KeystoreConfig(val alias: String, val storePath: String, val storePassword: String, val keyPassword: String)

interface BuildEngine {
	suspend fun build(project: ProjectRef, mode: BuildMode): BuildResult
	suspend fun sign(apkPath: String, keystore: KeystoreConfig): String
}