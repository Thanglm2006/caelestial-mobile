package com.example.crudfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentScreen()
        }
    }
}

// 🔥 UI CHÍNH
@Composable
fun StudentScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    var studentList by remember { mutableStateOf(listOf<Student>()) }
    var editingStudent by remember { mutableStateOf<Student?>(null) }

    val helper = remember { FirestoreHelper() }

    LaunchedEffect(true) {
        helper.getAll {
            studentList = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Student Manager", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Button(onClick = {
                val student = Student("", name, age.toIntOrNull() ?: 0)

                if (editingStudent == null) {
                    helper.add(student)
                } else {
                    student.id = editingStudent!!.id
                    helper.update(student)
                    editingStudent = null
                }

                name = ""
                age = ""

                helper.getAll { studentList = it }
            }) {
                Text(if (editingStudent == null) "Add" else "Update")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
                name = ""
                age = ""
                editingStudent = null
            }) {
                Text("Clear")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(studentList) { student ->
                StudentItem(
                    student = student,
                    onEdit = {
                        name = student.name
                        age = student.age.toString()
                        editingStudent = student
                    },
                    onDelete = {
                        helper.delete(student.id)
                        helper.getAll { studentList = it }
                    }
                )
            }
        }
    }
}

// 🔥 ITEM
@Composable
fun StudentItem(
    student: Student,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text("Name: ${student.name}")
            Text("Age: ${student.age}")

            Row {
                Button(onClick = onEdit) {
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}