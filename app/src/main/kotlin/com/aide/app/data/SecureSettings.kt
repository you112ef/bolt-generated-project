package com.aide.app.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.aide.app.ai.cloud.AiSettings
import com.aide.app.ai.cloud.AiSettingsProvider

class SecureSettingsRepository(context: Context) : AiSettingsProvider {
	private val prefs = EncryptedSharedPreferences.create(
		context,
		"secure_settings",
		MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
		EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
		EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
	)

	override suspend fun get(): AiSettings = AiSettings(
		provider = prefs.getString(KEY_PROVIDER, "openai") ?: "openai",
		model = prefs.getString(KEY_MODEL, "gpt-4o-mini") ?: "gpt-4o-mini",
		apiKey = prefs.getString(KEY_API_KEY, "") ?: ""
	)

	override suspend fun set(new: AiSettings) {
		prefs.edit()
			.putString(KEY_PROVIDER, new.provider)
			.putString(KEY_MODEL, new.model)
			.putString(KEY_API_KEY, new.apiKey)
			.apply()
	}

	companion object {
		private const val KEY_PROVIDER = "ai_provider"
		private const val KEY_MODEL = "ai_model"
		private const val KEY_API_KEY = "ai_api_key"
	}
}