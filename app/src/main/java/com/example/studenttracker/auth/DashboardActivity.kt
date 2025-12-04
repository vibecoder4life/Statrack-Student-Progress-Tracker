package com.example.studenttracker.dashboard

import android.content.Intent
import android.os.Bundle
import com.example.studenttracker.model.Student
import androidx.appcompat.app.AppCompatActivity
import com.example.studenttracker.auth.LoginActivity
import com.example.studenttracker.databinding.ActivityDashboardBinding
import com.example.studenttracker.students.AddStudentActivity
import com.example.studenttracker.students.StudentListActivity
import com.example.studenttracker.auth.StudentDetailsActivity
import com.example.studenttracker.students.ViewProgressActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val u = auth.currentUser
        binding.tvHello.text = if (u?.displayName.isNullOrEmpty()) "Hello, ${u?.email}" else "Hello, ${u?.displayName}"

        binding.btnAddStudents.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
        binding.btnStudentList.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }
        binding.btnViewProgress.setOnClickListener {
            startActivity(Intent(this, ViewProgressActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
