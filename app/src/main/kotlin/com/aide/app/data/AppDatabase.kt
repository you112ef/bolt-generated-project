package com.aide.app.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
	entities = [Note::class, Expense::class, Project::class, BuildJob::class],
	version = 2,
	exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun noteDao(): NoteDao
	abstract fun expenseDao(): ExpenseDao
	abstract fun projectDao(): ProjectDao
	abstract fun buildJobDao(): BuildJobDao

	companion object {
		@Volatile private var instance: AppDatabase? = null

		fun get(context: Context): AppDatabase = instance ?: synchronized(this) {
			instance ?: Room.databaseBuilder(
				context.applicationContext,
				AppDatabase::class.java,
				"aide.db"
			).fallbackToDestructiveMigration().build().also { instance = it }
		}
	}
}