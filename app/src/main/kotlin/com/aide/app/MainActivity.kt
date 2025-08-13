package com.aide.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aide.app.ui.HomeScreen
import com.aide.app.ui.EditorScreen
import com.aide.app.ui.BuildScreen
import com.aide.app.ui.SettingsScreen

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
	val colors = lightColorScheme()
	MaterialTheme(colorScheme = colors) {
		Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
			NavHost(navController = navController, startDestination = "home") {
				composable("home") { HomeScreen(navController) }
				composable("editor") { EditorScreen(navController) }
				composable("build") { BuildScreen(navController) }
				composable("settings") { SettingsScreen(navController) }
			}
		}
	}
}