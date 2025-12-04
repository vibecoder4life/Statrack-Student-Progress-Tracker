package com.example.studenttracker.students

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studenttracker.databinding.ItemProgressBinding
import com.example.studenttracker.model.Grade

class StudentProgressAdapter(
    private val list: List<Grade>
) : RecyclerView.Adapter<StudentProgressAdapter.ProgressViewHolder>() {

    inner class ProgressViewHolder(val binding: ItemProgressBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = ItemProgressBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val grade = list[position]

        holder.binding.tvSubject.text = grade.subject
        holder.binding.tvScore.text = "Score: ${grade.score}"
        holder.binding.tvDate.text = grade.date
    }

    override fun getItemCount(): Int = list.size
}
