package com.example.studenttracker.model

data class Student(
    var id: String = "",
    val name: String = "",
    val grade: String = "",
    val gradeLevel: String = "",
    val grades: Map<String, Double> = emptyMap()
) {
    val average: Double
        get() = if (grades.isNotEmpty()) grades.values.average() else 0.0
}
