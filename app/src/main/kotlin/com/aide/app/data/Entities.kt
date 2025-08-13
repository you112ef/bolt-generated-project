package com.aide.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notes")
data class Note(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val title: String,
	val description: String,
	val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "expenses")
data class Expense(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val amount: Double,
	val reason: String,
	val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface NoteDao {
	@Insert suspend fun insert(note: Note): Long
	@Update suspend fun update(note: Note)
	@Delete suspend fun delete(note: Note)
	@Query("SELECT * FROM notes ORDER BY createdAt DESC") fun observeAll(): Flow<List<Note>>
}

@Dao
interface ExpenseDao {
	@Insert suspend fun insert(expense: Expense): Long
	@Update suspend fun update(expense: Expense)
	@Delete suspend fun delete(expense: Expense)
	@Query("SELECT * FROM expenses ORDER BY createdAt DESC") fun observeAll(): Flow<List<Expense>>
}