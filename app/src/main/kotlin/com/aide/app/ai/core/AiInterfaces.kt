package com.aide.app.ai.core

import kotlinx.coroutines.flow.Flow

interface AiProvider {
	suspend fun chat(prompt: String, ctx: AiContext, tools: List<AiTool> = emptyList()): AiResponse
	suspend fun complete(prefix: String, suffix: String?, lang: String?, ctx: AiContext): String
	suspend fun embeddings(texts: List<String>): FloatArray
}

data class AiContext(
	val projectPath: String,
	val openFiles: List<OpenFile>,
	val selection: CodeSelection?,
	val buildLog: String?
)

data class OpenFile(val path: String, val content: String)

data class CodeSelection(val filePath: String, val start: Int, val end: Int)

data class AiTool(
	val name: String,
	val description: String,
	val parametersJsonSchema: String,
	val handler: suspend (Map<String, Any?>) -> ToolResult
)

data class ToolResult(val ok: Boolean, val message: String, val payload: Map<String, Any?> = emptyMap())

data class AiResponse(val text: String, val toolCalls: List<ToolInvocation> = emptyList())

data class ToolInvocation(val toolName: String, val args: Map<String, Any?>)