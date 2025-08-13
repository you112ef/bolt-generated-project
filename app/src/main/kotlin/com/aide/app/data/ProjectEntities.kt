package com.aide.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "projects")
data class Project(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val name: String,
	val path: String,
	val type: String,
	val lastOpened: Long = System.currentTimeMillis()
)

@Entity(tableName = "build_jobs")
data class BuildJob(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val projectId: Long,
	val mode: String,
	val status: String,
	val logsPath: String? = null,
	val artifactPath: String? = null,
	val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface ProjectDao {
	@Insert suspend fun insert(project: Project): Long
	@Update suspend fun update(project: Project)
	@Delete suspend fun delete(project: Project)
	@Query("SELECT * FROM projects ORDER BY lastOpened DESC") fun observeAll(): Flow<List<Project>>
	@Query("SELECT * FROM projects WHERE id = :id") suspend fun getById(id: Long): Project?
}

@Dao
interface BuildJobDao {
	@Insert suspend fun insert(job: BuildJob): Long
	@Update suspend fun update(job: BuildJob)
	@Query("SELECT * FROM build_jobs WHERE projectId = :projectId ORDER BY createdAt DESC") fun observeByProject(projectId: Long): Flow<List<BuildJob>>
}