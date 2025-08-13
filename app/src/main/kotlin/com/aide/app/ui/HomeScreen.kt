package com.aide.app.ui

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.aide.app.data.Project
import com.aide.app.data.ReposFactory
import com.aide.app.util.ProjectTemplateType
import com.aide.app.util.TemplateGenerator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(nav: NavController) {
	val vm: HomeViewModel = viewModel()
	var name by remember { mutableStateOf("MyProject") }
	var prompt by remember { mutableStateOf("") }

	Column(Modifier.fillMaxSize().padding(16.dp)) {
		Text("AIDE — Projects")
		Spacer(Modifier.height(6.dp))
		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			Button(onClick = { nav.navigate("settings") }) { Text("AI Settings") }
		}
		Spacer(Modifier.height(12.dp))
		Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Project Name") })
			OutlinedTextField(value = prompt, onValueChange = { prompt = it }, label = { Text("New from Prompt (optional)") })
		}
		Spacer(Modifier.height(8.dp))
		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			Button(onClick = { vm.createTemplate(ProjectTemplateType.KotlinAndroidCompose, name, prompt) }) { Text("Android Compose") }
			Button(onClick = { vm.createTemplate(ProjectTemplateType.SampleNotesExpenses, name, prompt) }) { Text("Sample Notes+Expenses") }
			Button(onClick = { vm.createTemplate(ProjectTemplateType.Flutter, name, prompt) }) { Text("Flutter") }
		}
		Spacer(Modifier.height(12.dp))
		Text("Recent Projects")
		LazyColumn {
			items(vm.projects) { p ->
				Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
					Text(p.name)
					Spacer(Modifier.weight(1f))
					Button(onClick = { nav.navigate("editor") }) { Text("Open") }
					Button(onClick = { nav.navigate("build") }) { Text("Build") }
				}
			}
		}
	}
}

class HomeViewModel(app: Application) : AndroidViewModel(app) {
	private val projectsRepo = ReposFactory.projects(app)

	var projects: List<Project> by mutableStateOf(emptyList())
		private set

	init {
		viewModelScope.launch { projectsRepo.observeProjects().collectLatest { projects = it } }
	}

	fun createTemplate(type: ProjectTemplateType, name: String, prompt: String?) {
		viewModelScope.launch {
			val root = TemplateGenerator.generateProject(getApplication(), name, type, prompt)
			projectsRepo.createProject(name, root.absolutePath, type.name)
		}
	}
}