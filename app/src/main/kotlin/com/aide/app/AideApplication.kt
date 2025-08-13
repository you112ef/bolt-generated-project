package com.aide.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration

class AideApplication : Application(), Configuration.Provider {
	override fun onCreate() {
		super.onCreate()
		// Initialize singletons, DI, databases lazily when first used
	}

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder()
			.setMinimumLoggingLevel(Log.INFO)
			.build()
}