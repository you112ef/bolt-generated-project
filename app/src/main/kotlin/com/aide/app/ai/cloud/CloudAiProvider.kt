package com.aide.app.ai.cloud

import com.aide.app.ai.core.*

class CloudAiProvider(private val settings: AiSettingsProvider) : AiProvider {
	override suspend fun chat(prompt: String, ctx: AiContext, tools: List<AiTool>): AiResponse {
		val conf = settings.get()
		val header = "[${conf.provider}:${conf.model}]"
		return AiResponse(text = "$header $prompt")
	}

	override suspend fun complete(prefix: String, suffix: String?, lang: String?, ctx: AiContext): String {
		val conf = settings.get()
		return "" // placeholder
	}

	override suspend fun embeddings(texts: List<String>): FloatArray = FloatArray(texts.size) { 0f }
}

interface AiSettingsProvider {
	suspend fun get(): AiSettings
	suspend fun set(new: AiSettings)
}

data class AiSettings(val provider: String, val model: String, val apiKey: String)