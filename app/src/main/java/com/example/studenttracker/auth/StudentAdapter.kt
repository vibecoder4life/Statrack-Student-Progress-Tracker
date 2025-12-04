package com.example.studenttracker.students

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studenttracker.databinding.ItemStudentBinding
import com.example.studenttracker.model.Student
import com.example.studenttracker.auth.StudentDetailsActivity

class StudentAdapter(
    private val onClick: (Student) -> Unit  // Lambda for click events
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var students: List<Student> = emptyList() // Dynamic list

    inner class StudentViewHolder(val binding: ItemStudentBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.binding.tvName.text = student.name
        holder.binding.tvGrade.text = "Grade: ${student.grade}"
        holder.binding.tvAverage.text = "Average: %.2f".format(student.average)

        // Click listener using lambda
        holder.binding.root.setOnClickListener {
            onClick(student)
        }
    }

    override fun getItemCount(): Int = students.size

    // Function to update the list dynamically
    fun submitList(list: List<Student>) {
        students = list
        notifyDataSetChanged()
    }
}
