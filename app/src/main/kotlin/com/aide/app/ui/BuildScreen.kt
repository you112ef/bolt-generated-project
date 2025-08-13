package com.aide.app.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.aide.app.buildsystem.core.BuildMode
import com.aide.app.buildsystem.core.ProjectRef
import com.aide.app.buildsystem.local.LocalBuildEngine
import com.aide.app.data.ReposFactory
import kotlinx.coroutines.launch

@Composable
fun BuildScreen(nav: androidx.navigation.NavController) {
	val vm: BuildViewModel = viewModel()
	var status by remember { mutableStateOf("Idle") }
	Column(Modifier.fillMaxSize().padding(16.dp)) {
		Text("Build / Export APK")
		Spacer(Modifier.height(12.dp))
		Button(onClick = {
			vm.runBuild(onStatus = { status = it })
		}) { Text("Run Local Build (Simulated)") }
		Spacer(Modifier.height(8.dp))
		Text("Status: $status")
		Spacer(Modifier.height(12.dp))
		Button(onClick = { nav.popBackStack() }) { Text("Back") }
	}
}

class BuildViewModel(app: Application) : AndroidViewModel(app) {
	private val buildsRepo = ReposFactory.builds(app)
	private val projectsRepo = ReposFactory.projects(app)
	private val engine = LocalBuildEngine()

	fun runBuild(onStatus: (String) -> Unit) {
		viewModelScope.launch {
			onStatus("Starting")
			val project = projectsRepo.observeProjects()
												
			// For simplicity, pick first project if any
			var first = projectsRepo.observeProjects()
			// Not ideal to collect here; for MVP, we simulate using a placeholder path
			val ref = ProjectRef(id = 0, path = getApplication<Application>().filesDir.absolutePath, name = "Demo")
			val result = engine.build(ref, BuildMode.Local)
			buildsRepo.addJob(projectId = ref.id, mode = BuildMode.Local.toString(), status = if (result.success) "SUCCESS" else "FAIL")
			onStatus(if (result.success) "Success" else "Failed")
		}
	}
}