package com.example.smartstudentdirectory

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                StudentDirectoryApp()
            }
        }
    }
}


data class Student(
    val name: String,
    val registerNumber: String,
    val department: String,
    val gender: String
)


@Composable
fun StudentDirectoryApp() {

    val navController = rememberNavController()

    val students = listOf(

        Student(
            name = "Diana",
            registerNumber = "23CSE001",
            department = "CSE",
            gender = "Female"
        ),

        Student(
            name = "Alvin",
            registerNumber = "23CSE002",
            department = "CSE",
            gender = "Male"
        ),

        Student(
            name = "Adrina",
            registerNumber = "23CSE003",
            department = "CSE",
            gender = "Female"
        ),

        Student(
            name = "Ardina",
            registerNumber = "23CSE013",
            department = "ECE",
            gender = "Female"
        ),

        Student(
            name = "John",
            registerNumber = "23CSE004",
            department = "CSE",
            gender = "Male"
        ),

        Student(
            name = "Rachel",
            registerNumber = "23CSE005",
            department = "CSE",
            gender = "Female"
        ),

        Student(
            name = "David",
            registerNumber = "23CSE006",
            department = "CSE",
            gender = "Male"
        ),

        Student(
            name = "Sarah",
            registerNumber = "23CSE007",
            department = "CSE",
            gender = "Female"
        )
    )


    NavHost(
        navController = navController,
        startDestination = "students"
    ) {

        composable("students") {

            StudentListScreen(
                students = students,

                onStudentClick = { student ->

                    val name = Uri.encode(student.name)
                    val registerNumber = Uri.encode(student.registerNumber)
                    val department = Uri.encode(student.department)
                    val gender = Uri.encode(student.gender)

                    navController.navigate(
                        "details/$name/$registerNumber/$department/$gender"
                    )
                }
            )
        }


        composable(
            "details/{name}/{registerNumber}/{department}/{gender}"
        ) { backStackEntry ->

            val name =
                backStackEntry.arguments
                    ?.getString("name")
                    ?: ""

            val registerNumber =
                backStackEntry.arguments
                    ?.getString("registerNumber")
                    ?: ""

            val department =
                backStackEntry.arguments
                    ?.getString("department")
                    ?: ""

            val gender =
                backStackEntry.arguments
                    ?.getString("gender")
                    ?: ""


            StudentDetailsScreen(
                name = name,
                registerNumber = registerNumber,
                department = department,
                gender = gender,

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


@Composable
fun StudentListScreen(
    students: List<Student>,
    onStudentClick: (Student) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Smart Student Directory",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(students) { student ->

                StudentItem(
                    student = student,

                    onClick = {
                        onStudentClick(student)
                    }
                )
            }
        }
    }
}


@Composable
fun StudentItem(
    student: Student,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = student.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Register Number: " +
                        student.registerNumber
            )

            Text(
                text = "Department: " +
                        student.department
            )
        }
    }
}


@Composable
fun StudentDetailsScreen(
    name: String,
    registerNumber: String,
    department: String,
    gender: String,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Student Details",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Name: $name"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Register Number: $registerNumber"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Department: $department"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Gender: $gender"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }
}