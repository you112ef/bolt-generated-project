package com.aide.app.ai.local

import com.aide.app.ai.core.*

class NoopLocalAiProvider : AiProvider {
	override suspend fun chat(prompt: String, ctx: AiContext, tools: List<AiTool>): AiResponse {
		return AiResponse(text = "[Local AI placeholder] $prompt")
	}

	override suspend fun complete(prefix: String, suffix: String?, lang: String?, ctx: AiContext): String {
		return ""
	}

	override suspend fun embeddings(texts: List<String>): FloatArray = FloatArray(texts.size) { 0f }
}