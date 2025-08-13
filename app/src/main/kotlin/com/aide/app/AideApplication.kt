package com.aide.app

import android.app.Application
import androidx.work.Configuration

class AideApplication : Application(), Configuration.Provider {
	override fun onCreate() {
		super.onCreate()
		// Initialize singletons, DI, databases lazily when first used
	}

	override fun getWorkManagerConfiguration(): Configuration =
		Configuration.Builder()
			.setMinimumLoggingLevel(android.util.Log.INFO)
			.build()
}