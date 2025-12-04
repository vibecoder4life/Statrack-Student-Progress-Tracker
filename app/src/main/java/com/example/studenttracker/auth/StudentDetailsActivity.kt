package com.example.studenttracker.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenttracker.databinding.ActivityStudentDetailsBinding
import com.example.studenttracker.firebase.FirestoreRepository
import com.example.studenttracker.model.Grade
import com.example.studenttracker.students.GradeAdapter

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private val repo = FirestoreRepository()
    private var studentId: String = ""  // Will come from intent

    private lateinit var gradesAdapter: GradeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get student ID from intent
        studentId = intent.getStringExtra("STUDENT_ID") ?: ""

        setupRecyclerView()
        listenGrades()

        binding.btnAddGrade.setOnClickListener {
            val subject = binding.etSubject.text.toString().trim()
            val scoreText = binding.etScore.text.toString().trim()

            if (subject.isEmpty() || scoreText.isEmpty()) {
                Toast.makeText(this, "Enter subject and score", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val score = scoreText.toDoubleOrNull()
            if (score == null || score !in 0.0..100.0) {
                Toast.makeText(this, "Score must be 0-100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val grade = Grade(subject = subject, score = score)

            repo.addGrade(studentId, grade) { success ->
                if (success) {
                    Toast.makeText(this, "Grade added", Toast.LENGTH_SHORT).show()
                    binding.etSubject.text?.clear()
                    binding.etScore.text?.clear()
                    listenGrades()  // Refresh grades
                } else {
                    Toast.makeText(this, "Failed to add grade", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        gradesAdapter = GradeAdapter(
            onUpdate = { grade ->
                // TODO: Handle grade update (optional)
            },
            onDelete = { grade ->
                repo.deleteGrade(studentId, grade.id) { success ->
                    if (success) {
                        Toast.makeText(this, "Grade deleted", Toast.LENGTH_SHORT).show()
                        listenGrades()
                    } else {
                        Toast.makeText(this, "Failed to delete grade", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        binding.recyclerGrades.layoutManager = LinearLayoutManager(this)
        binding.recyclerGrades.adapter = gradesAdapter
    }

    private fun listenGrades() {
        repo.getStudentGrades(studentId) { grades ->
            gradesAdapter.submitList(grades)
        }
    }
}
