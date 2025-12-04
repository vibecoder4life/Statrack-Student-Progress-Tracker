package com.example.studenttracker.model

data class Grade(
    var id: String = "",   // <-- must be 'var'
    val subject: String = "",
    val score: Double = 0.0,
    val remarks: String = "",
    val date: String = ""

)


