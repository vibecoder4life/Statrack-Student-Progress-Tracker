package com.example.studenttracker.students

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studenttracker.databinding.ActivityAddStudentBinding
import com.example.studenttracker.firebase.FirestoreRepository
import com.example.studenttracker.model.Student
import java.util.*


class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private val repo = FirestoreRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val grade = binding.etGrade.text.toString().trim()

            if (name.isEmpty() || grade.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(
                id = UUID.randomUUID().toString(),
                name = name,
                gradeLevel = grade
            )

            binding.progressBar.visibility = View.VISIBLE
            repo.addStudent(student) { success ->
                binding.progressBar.visibility = View.GONE
                if (success) {
                    Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
