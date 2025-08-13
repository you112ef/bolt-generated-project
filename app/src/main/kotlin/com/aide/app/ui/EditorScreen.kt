package com.aide.app.ui

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.weight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.aide.app.data.Expense
import com.aide.app.data.Note
import com.aide.app.data.ReposFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditorScreen(nav: androidx.navigation.NavController) {
	val vm: EditorViewModel = viewModel()
	val notes = vm.notes
	val expenses = vm.expenses

	Column(Modifier.fillMaxSize().padding(16.dp)) {
		Text("Quick Notes & Expenses — Sample")
		Spacer(Modifier.height(12.dp))
		Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			NoteInput(onAdd = { title, desc -> vm.addNote(title, desc) })
			ExpenseInput(onAdd = { amount, reason -> vm.addExpense(amount, reason) })
		}
		Spacer(Modifier.height(12.dp))
		Row(Modifier.fillMaxWidth()) {
			Column(Modifier.weight(1f)) {
				Text("Notes")
				LazyColumn { items(notes) { n -> Text("• ${n.title} — ${n.description}") } }
			}
			Column(Modifier.weight(1f)) {
				Text("Expenses")
				LazyColumn { items(expenses) { e -> Text("• ${"%.2f".format(e.amount)} — ${e.reason}") } }
			}
		}
		Spacer(Modifier.height(12.dp))
		Button(onClick = { nav.popBackStack() }) { Text("Back") }
	}
}

@Composable
private fun NoteInput(onAdd: (String, String) -> Unit) {
	var title by remember { mutableStateOf("") }
	var desc by remember { mutableStateOf("") }
	Column(Modifier.weight(1f)) {
		OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
		OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
		Button(onClick = { if (title.isNotBlank()) { onAdd(title, desc); title = ""; desc = "" } }) { Text("Add Note") }
	}
}

@Composable
private fun ExpenseInput(onAdd: (Double, String) -> Unit) {
	var amount by remember { mutableStateOf("") }
	var reason by remember { mutableStateOf("") }
	Column(Modifier.weight(1f)) {
		OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
		OutlinedTextField(value = reason, onValueChange = { reason = it }, label = { Text("Reason") })
		Button(onClick = { amount.toDoubleOrNull()?.let { onAdd(it, reason); amount = ""; reason = "" } }) { Text("Add Expense") }
	}
}

class EditorViewModel(app: Application) : AndroidViewModel(app) {
	private val notesRepo = ReposFactory.notes(app)
	private val expensesRepo = ReposFactory.expenses(app)

	var notes: List<Note> by mutableStateOf(emptyList())
		private set
	var expenses: List<Expense> by mutableStateOf(emptyList())
		private set

	init {
		viewModelScope.launch {
			notesRepo.observeNotes().collectLatest { notes = it }
		}
		viewModelScope.launch {
			expensesRepo.observeExpenses().collectLatest { expenses = it }
		}
	}

	fun addNote(title: String, desc: String) {
		viewModelScope.launch { notesRepo.addNote(title, desc) }
	}

	fun addExpense(amount: Double, reason: String) {
		viewModelScope.launch { expensesRepo.addExpense(amount, reason) }
	}
}