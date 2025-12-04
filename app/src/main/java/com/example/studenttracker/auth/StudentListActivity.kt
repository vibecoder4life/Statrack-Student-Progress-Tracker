package com.example.studenttracker.students

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studenttracker.databinding.ActivityStudentListBinding
import com.example.studenttracker.firebase.FirestoreRepository
import com.example.studenttracker.model.Student
import com.example.studenttracker.students.AddStudentActivity
import com.example.studenttracker.auth.StudentDetailsActivity

class StudentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentListBinding
    private val repo = FirestoreRepository()
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize adapter with click listener
        adapter = StudentAdapter { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("studentId", student.id)
            intent.putExtra("studentName", student.name)
            startActivity(intent)
        }

        // RecyclerView setup
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        // Add Student button
        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        // Load students from Firestore
        repo.getStudents { students ->
            adapter.submitList(students)
        }
    }
}
