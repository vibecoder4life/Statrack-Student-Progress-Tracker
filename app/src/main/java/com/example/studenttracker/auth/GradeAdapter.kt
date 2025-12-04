package com.example.studenttracker.students

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studenttracker.databinding.ItemGradeBinding
import com.example.studenttracker.model.Grade

class GradeAdapter(
    private val onUpdate: (Grade) -> Unit,
    private val onDelete: (Grade) -> Unit
) : ListAdapter<Grade, GradeAdapter.VH>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Grade>() {
            override fun areItemsTheSame(oldItem: Grade, newItem: Grade) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Grade, newItem: Grade) = oldItem == newItem
        }
    }

    inner class VH(private val binding: ItemGradeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(grade: Grade) {
            binding.tvSubject.text = grade.subject
            binding.tvScore.text = grade.score.toInt().toString()
            binding.tvRemarks.text = if (grade.score >= 75) "Passed" else "Failed"

            binding.btnUpdate.setOnClickListener { onUpdate(grade) }
            binding.btnDelete.setOnClickListener { onDelete(grade) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemGradeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}
