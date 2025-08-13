package com.aide.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aide.app.ui.HomeScreen
import com.aide.app.ui.EditorScreen
import com.aide.app.ui.BuildScreen

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			App()
		}
	}
}

@Composable
fun App() {
	val navController = rememberNavController()
	MaterialTheme {
		Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
			NavHost(navController = navController, startDestination = "home") {
				composable("home") { HomeScreen(navController) }
				composable("editor") { EditorScreen(navController) }
				composable("build") { BuildScreen(navController) }
				composable("settings") { com.aide.app.ui.SettingsScreen(navController) }
			}
		}
	}
}