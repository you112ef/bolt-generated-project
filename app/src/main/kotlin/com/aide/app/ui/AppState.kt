package com.aide.app.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.aide.app.data.Project

class AppState {
	var selectedProject: Project? by mutableStateOf(null)
}

val LocalAppState = staticCompositionLocalOf { AppState() }