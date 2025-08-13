package com.aide.app.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val db: AppDatabase) {
	fun observeNotes(): Flow<List<Note>> = db.noteDao().observeAll()
	suspend fun addNote(title: String, description: String) {
		db.noteDao().insert(Note(title = title, description = description))
	}
}

class ExpensesRepository(private val db: AppDatabase) {
	fun observeExpenses(): Flow<List<Expense>> = db.expenseDao().observeAll()
	suspend fun addExpense(amount: Double, reason: String) {
		db.expenseDao().insert(Expense(amount = amount, reason = reason))
	}
}

class ProjectsRepository(private val db: AppDatabase) {
	fun observeProjects(): Flow<List<Project>> = db.projectDao().observeAll()
	suspend fun createProject(name: String, path: String, type: String): Long =
		db.projectDao().insert(Project(name = name, path = path, type = type))
}

class BuildJobsRepository(private val db: AppDatabase) {
	fun observeByProject(projectId: Long): Flow<List<BuildJob>> = db.buildJobDao().observeByProject(projectId)
	suspend fun addJob(projectId: Long, mode: String, status: String, logsPath: String? = null, artifactPath: String? = null): Long =
		db.buildJobDao().insert(BuildJob(projectId = projectId, mode = mode, status = status, logsPath = logsPath, artifactPath = artifactPath))
}

object ReposFactory {
	fun notes(context: Context) = NotesRepository(AppDatabase.get(context))
	fun expenses(context: Context) = ExpensesRepository(AppDatabase.get(context))
	fun projects(context: Context) = ProjectsRepository(AppDatabase.get(context))
	fun builds(context: Context) = BuildJobsRepository(AppDatabase.get(context))
}