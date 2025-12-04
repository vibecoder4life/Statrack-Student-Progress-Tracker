package com.example.studenttracker.students

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenttracker.databinding.ViewProgressBinding
import com.example.studenttracker.firebase.FirestoreRepository

class ViewProgressActivity : AppCompatActivity() {

    private lateinit var binding: ViewProgressBinding
    private val repo = FirestoreRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProgress.layoutManager = LinearLayoutManager(this)
        loadOverallProgress()
    }

    private fun loadOverallProgress() {
        repo.getStudents { students ->
            val grades = students.flatMap { it.grades.map { entry ->
                com.example.studenttracker.model.Grade("", entry.key, entry.value, "")
            } }

            val adapter = StudentProgressAdapter(grades)
            binding.recyclerProgress.adapter = adapter

            val avg = if (grades.isNotEmpty()) grades.map { it.score }.average() else 0.0
            binding.progressOverall.progress = avg.toInt()
            binding.tvProgressPercent.text = "Overall: %.1f%%".format(avg)
            binding.tvAverageGrade.text = "Average Grade: %.2f".format(avg)
            binding.tvPassed.text = "Passed: ${grades.count { it.score >= 75 }}"
            binding.tvFailed.text = "Failed: ${grades.count { it.score < 75 }}"
        }
    }
}
