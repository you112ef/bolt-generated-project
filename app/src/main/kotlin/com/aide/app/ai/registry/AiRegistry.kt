package com.aide.app.ai.registry

object AiRegistry {
	data class Model(val id: String, val label: String, val maxTokens: Int? = null)
	data class Provider(
		val name: String,
		val apiKeyEnv: String? = null,
		val baseUrlEnv: String? = null,
		val models: List<Model> = emptyList(),
		val notes: String? = null
	)

	val providers: List<Provider> = listOf(
		Provider(
			name = "OpenAI",
			apiKeyEnv = "OPENAI_API_KEY",
			models = listOf(
				Model("gpt-4o", "GPT-4o", 8000),
				Model("gpt-4o-mini", "GPT-4o Mini", 8000),
				Model("gpt-4-turbo", "GPT-4 Turbo", 8000),
				Model("gpt-4", "GPT-4", 8000),
				Model("gpt-3.5-turbo", "GPT-3.5 Turbo", 8000)
			)
		),
		Provider(
			name = "Anthropic",
			apiKeyEnv = "ANTHROPIC_API_KEY",
			models = listOf(
				Model("claude-3-7-sonnet-20250219", "Claude 3.7 Sonnet", 128000),
				Model("claude-3-5-sonnet-latest", "Claude 3.5 Sonnet (new)", 8000),
				Model("claude-3-5-sonnet-20240620", "Claude 3.5 Sonnet (old)", 8000),
				Model("claude-3-5-haiku-latest", "Claude 3.5 Haiku (new)", 8000),
				Model("claude-3-opus-latest", "Claude 3 Opus", 8000),
				Model("claude-3-sonnet-20240229", "Claude 3 Sonnet", 8000),
				Model("claude-3-haiku-20240307", "Claude 3 Haiku", 8000)
			)
		),
		Provider(
			name = "Google",
			apiKeyEnv = "GOOGLE_GENERATIVE_AI_API_KEY",
			models = listOf(
				Model("gemini-1.5-flash-latest", "Gemini 1.5 Flash", 8192),
				Model("gemini-2.0-flash-thinking-exp-01-21", "Gemini 2.0 Flash-thinking-exp-01-21", 65536),
				Model("gemini-2.0-flash-exp", "Gemini 2.0 Flash", 8192),
				Model("gemini-1.5-flash-002", "Gemini 1.5 Flash-002", 8192),
				Model("gemini-1.5-flash-8b", "Gemini 1.5 Flash-8b", 8192),
				Model("gemini-1.5-pro-latest", "Gemini 1.5 Pro", 8192),
				Model("gemini-1.5-pro-002", "Gemini 1.5 Pro-002", 8192),
				Model("gemini-exp-1206", "Gemini exp-1206", 8192)
			)
		),
		Provider(
			name = "Groq",
			apiKeyEnv = "GROQ_API_KEY",
			models = listOf(
				Model("llama-3.1-8b-instant", "Llama 3.1 8b (Groq)", 8000),
				Model("llama-3.2-11b-vision-preview", "Llama 3.2 11b (Groq)", 8000),
				Model("llama-3.2-90b-vision-preview", "Llama 3.2 90b (Groq)", 8000),
				Model("llama-3.2-3b-preview", "Llama 3.2 3b (Groq)", 8000),
				Model("llama-3.2-1b-preview", "Llama 3.2 1b (Groq)", 8000),
				Model("llama-3.3-70b-versatile", "Llama 3.3 70b (Groq)", 8000),
				Model("deepseek-r1-distill-llama-70b", "Deepseek R1 Distill Llama 70b (Groq)", 131072)
			)
		),
		Provider(
			name = "Deepseek",
			apiKeyEnv = "DEEPSEEK_API_KEY",
			models = listOf(
				Model("deepseek-coder", "Deepseek-Coder", 8000),
				Model("deepseek-chat", "Deepseek-Chat", 8000),
				Model("deepseek-reasoner", "Deepseek-Reasoner", 8000)
			)
		),
		Provider(
			name = "Mistral",
			apiKeyEnv = "MISTRAL_API_KEY",
			models = listOf(
				Model("open-mistral-7b", "Mistral 7B", 8000),
				Model("open-mixtral-8x7b", "Mistral 8x7B", 8000),
				Model("open-mixtral-8x22b", "Mistral 8x22B", 8000),
				Model("open-codestral-mamba", "Codestral Mamba", 8000),
				Model("open-mistral-nemo", "Mistral Nemo", 8000),
				Model("ministral-8b-latest", "Mistral 8B", 8000),
				Model("mistral-small-latest", "Mistral Small", 8000),
				Model("codestral-latest", "Codestral", 8000),
				Model("mistral-large-latest", "Mistral Large Latest", 8000)
			)
		),
		Provider(
			name = "Cohere",
			apiKeyEnv = "COHERE_API_KEY",
			models = listOf(
				Model("command-r-plus-08-2024", "Command R plus Latest", 4096),
				Model("command-r-08-2024", "Command R Latest", 4096),
				Model("command-r-plus", "Command R plus", 4096),
				Model("command-r", "Command R", 4096),
				Model("command", "Command", 4096),
				Model("command-nightly", "Command Nightly", 4096),
				Model("command-light", "Command Light", 4096),
				Model("command-light-nightly", "Command Light Nightly", 4096),
				Model("c4ai-aya-expanse-8b", "c4AI Aya Expanse 8b", 4096),
				Model("c4ai-aya-expanse-32b", "c4AI Aya Expanse 32b", 4096)
			)
		),
		Provider(
			name = "AmazonBedrock",
			apiKeyEnv = "AWS_BEDROCK_CONFIG",
			models = listOf(
				Model("anthropic.claude-3-5-sonnet-20241022-v2:0", "Claude 3.5 Sonnet v2 (Bedrock)", 200000),
				Model("anthropic.claude-3-5-sonnet-20240620-v1:0", "Claude 3.5 Sonnet (Bedrock)", 4096),
				Model("anthropic.claude-3-sonnet-20240229-v1:0", "Claude 3 Sonnet (Bedrock)", 4096),
				Model("anthropic.claude-3-haiku-20240307-v1:0", "Claude 3 Haiku (Bedrock)", 4096),
				Model("amazon.nova-pro-v1:0", "Amazon Nova Pro (Bedrock)", 5120),
				Model("amazon.nova-lite-v1:0", "Amazon Nova Lite (Bedrock)", 5120),
				Model("mistral.mistral-large-2402-v1:0", "Mistral Large 24.02 (Bedrock)", 8192)
			)
		),
		Provider(
			name = "HuggingFace",
			apiKeyEnv = "HuggingFace_API_KEY",
			models = listOf(
				Model("Qwen/Qwen2.5-Coder-32B-Instruct", "Qwen2.5-Coder-32B-Instruct"),
				Model("01-ai/Yi-1.5-34B-Chat", "Yi-1.5-34B-Chat"),
				Model("codellama/CodeLlama-34b-Instruct-hf", "CodeLlama-34b-Instruct"),
				Model("NousResearch/Hermes-3-Llama-3.1-8B", "Hermes-3-Llama-3.1-8B"),
				Model("Qwen/Qwen2.5-72B-Instruct", "Qwen2.5-72B-Instruct"),
				Model("meta-llama/Llama-3.1-70B-Instruct", "Llama-3.1-70B-Instruct"),
				Model("meta-llama/Llama-3.1-405B", "Llama-3.1-405B")
			)
		),
		Provider(
			name = "Hyperbolic",
			apiKeyEnv = "HYPERBOLIC_API_KEY",
			models = listOf(
				Model("Qwen/Qwen2.5-Coder-32B-Instruct", "Qwen 2.5 Coder 32B Instruct", 8192),
				Model("Qwen/Qwen2.5-72B-Instruct", "Qwen2.5-72B-Instruct", 8192),
				Model("deepseek-ai/DeepSeek-V2.5", "DeepSeek-V2.5", 8192),
				Model("Qwen/QwQ-32B-Preview", "QwQ-32B-Preview", 8192),
				Model("Qwen/Qwen2-VL-72B-Instruct", "Qwen2-VL-72B-Instruct", 8192)
			)
		),
		Provider(
			name = "OpenRouter",
			apiKeyEnv = "OPEN_ROUTER_API_KEY",
			models = listOf(
				Model("anthropic/claude-3.5-sonnet", "Anthropic: Claude 3.5 Sonnet (OpenRouter)", 8000),
				Model("anthropic/claude-3-haiku", "Anthropic: Claude 3 Haiku (OpenRouter)", 8000),
				Model("deepseek/deepseek-coder", "Deepseek-Coder V2 236B (OpenRouter)", 8000),
				Model("google/gemini-flash-1.5", "Google Gemini Flash 1.5 (OpenRouter)", 8000),
				Model("google/gemini-pro-1.5", "Google Gemini Pro 1.5 (OpenRouter)", 8000),
				Model("x-ai/grok-beta", "xAI Grok Beta (OpenRouter)", 8000),
				Model("mistralai/mistral-nemo", "OpenRouter Mistral Nemo (OpenRouter)", 8000),
				Model("qwen/qwen-110b-chat", "OpenRouter Qwen 110b Chat (OpenRouter)", 8000),
				Model("cohere/command", "Cohere Command (OpenRouter)", 4096)
			)
		),
		Provider(
			name = "Github",
			apiKeyEnv = "GITHUB_API_KEY",
			models = listOf(
				Model("gpt-4o", "GPT-4o", 8000),
				Model("o1", "o1-preview", 100000),
				Model("o1-mini", "o1-mini", 8000),
				Model("gpt-4o-mini", "GPT-4o Mini", 8000),
				Model("gpt-4-turbo", "GPT-4 Turbo", 8000),
				Model("gpt-4", "GPT-4", 8000),
				Model("gpt-3.5-turbo", "GPT-3.5 Turbo", 8000)
			)
		),
		Provider(
			name = "Perplexity",
			apiKeyEnv = "PERPLEXITY_API_KEY",
			models = listOf(
				Model("llama-3.1-sonar-small-128k-online", "Sonar Small Online", 8192),
				Model("llama-3.1-sonar-large-128k-online", "Sonar Large Online", 8192),
				Model("llama-3.1-sonar-huge-128k-online", "Sonar Huge Online", 8192)
			)
		),
		Provider(
			name = "Together",
			apiKeyEnv = "TOGETHER_API_KEY",
			baseUrlEnv = "TOGETHER_API_BASE_URL",
			models = listOf(
				Model("Qwen/Qwen2.5-Coder-32B-Instruct", "Qwen/Qwen2.5-Coder-32B-Instruct", 8000),
				Model("meta-llama/Llama-3.2-90B-Vision-Instruct-Turbo", "Llama-3.2-90B-Vision-Instruct-Turbo", 8000),
				Model("mistralai/Mixtral-8x7B-Instruct-v0.1", "Mixtral 8x7B Instruct", 8192)
			)
		),
		Provider(
			name = "Ollama",
			baseUrlEnv = "OLLAMA_API_BASE_URL",
			models = emptyList(),
			notes = "Local provider; models are discovered dynamically from /api/tags"
		),
		Provider(
			name = "LMStudio",
			baseUrlEnv = "LMSTUDIO_API_BASE_URL",
			models = emptyList(),
			notes = "Local provider; models are discovered dynamically from /v1/models"
		),
		Provider(
			name = "xAI",
			apiKeyEnv = "XAI_API_KEY",
			models = listOf(
				Model("grok-3-beta", "xAI Grok 3 Beta", 8000),
				Model("grok-beta", "xAI Grok Beta", 8000),
				Model("grok-2-1212", "xAI Grok2 1212", 8000)
			)
		)
	)

	fun providerNames(): List<String> = providers.map { it.name }
	fun modelsForProvider(providerName: String): List<Model> = providers.firstOrNull { it.name.equals(providerName, ignoreCase = true) }?.models ?: emptyList()
}