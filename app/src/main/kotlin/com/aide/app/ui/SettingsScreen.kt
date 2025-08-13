package com.aide.app.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.aide.app.ai.cloud.AiSettings
import com.aide.app.data.SecureSettingsRepository
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(nav: NavController) {
	val vm: SettingsViewModel = viewModel()
	var provider by remember { mutableStateOf(vm.provider) }
	var model by remember { mutableStateOf(vm.model) }
	var apiKey by remember { mutableStateOf(vm.apiKey) }
	Column(Modifier.fillMaxSize().padding(16.dp)) {
		Text("AI Settings")
		Spacer(Modifier.height(8.dp))
		OutlinedTextField(value = provider, onValueChange = { provider = it }, label = { Text("Provider (e.g., openai, anthropic)") })
		OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model (e.g., gpt-4o-mini)") })
		OutlinedTextField(value = apiKey, onValueChange = { apiKey = it }, label = { Text("API Key") })
		Spacer(Modifier.height(12.dp))
		Button(onClick = { vm.save(provider, model, apiKey); nav.popBackStack() }) { Text("Save") }
	}
}

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
	private val repo = SecureSettingsRepository(app)
	var provider: String = "openai"
		private set
	var model: String = "gpt-4o-mini"
		private set
	var apiKey: String = ""
		private set

	init {
		viewModelScope.launch {
			val s = repo.get()
			provider = s.provider
			model = s.model
			apiKey = s.apiKey
		}
	}

	fun save(provider: String, model: String, apiKey: String) {
		viewModelScope.launch { repo.set(AiSettings(provider, model, apiKey)) }
	}
}