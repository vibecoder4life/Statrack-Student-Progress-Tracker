package com.example.studenttracker.students

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenttracker.databinding.ActivityViewProgressBinding
import com.example.studenttracker.firebase.FirestoreRepository

class StudentProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewProgressBinding
    private val repo = FirestoreRepository()
    private var studentId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra("studentId") ?: ""

        binding.recyclerProgress.layoutManager = LinearLayoutManager(this)
        loadProgress()
    }

    private fun loadProgress() {
        repo.getStudentGrades(studentId) { grades ->
            val adapter = StudentProgressAdapter(grades)
            binding.recyclerProgress.adapter = adapter

            val avg = if (grades.isNotEmpty()) grades.map { it.score }.average() else 0.0
            binding.tvProgressPercent.text = "%.1f%%".format(avg)
            binding.tvAverageGrade.text = "Average: %.2f".format(avg)

            val passed = grades.count { it.score >= 75 }
            val failed = grades.size - passed

            binding.tvPassed.text = "Passed: $passed"
            binding.tvFailed.text = "Failed: $failed"
        }
    }
}
