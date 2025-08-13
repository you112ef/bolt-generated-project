package com.aide.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(nav: NavController) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("AIDE — Android IDE (MVP)")
		Spacer(Modifier.height(16.dp))
		Button(onClick = { nav.navigate("editor") }) { Text("Open Editor") }
		Spacer(Modifier.height(8.dp))
		Button(onClick = { nav.navigate("build") }) { Text("Build / Export APK") }
	}
}

@Composable
fun EditorScreen(nav: NavController) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Editor placeholder — integrate Text engine, Tree-sitter, LSP")
		Spacer(Modifier.height(12.dp))
		Button(onClick = { nav.popBackStack() }) { Text("Back") }
	}
}

@Composable
fun BuildScreen(nav: NavController) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text("Build Engine placeholder — run local/cloud builds")
		Spacer(Modifier.height(12.dp))
		Button(onClick = { nav.popBackStack() }) { Text("Back") }
	}
}