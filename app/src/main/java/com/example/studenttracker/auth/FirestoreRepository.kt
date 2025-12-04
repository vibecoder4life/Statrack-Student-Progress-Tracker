package com.example.studenttracker.firebase

import com.example.studenttracker.model.Grade
import com.example.studenttracker.model.Student
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    // Get all students
    fun getStudents(callback: (List<Student>) -> Unit) {
        firestore.collection("students")
            .get()
            .addOnSuccessListener { snapshot ->
                val students = snapshot.documents.mapNotNull { doc ->
                    val student = doc.toObject(Student::class.java)
                    student?.id = doc.id
                    student
                }
                callback(students)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    // Get grades of a student
    fun getStudentGrades(studentId: String, callback: (List<Grade>) -> Unit) {
        firestore.collection("students")
            .document(studentId)
            .collection("grades")
            .get()
            .addOnSuccessListener { snapshot ->
                val grades = snapshot.documents.mapNotNull { doc ->
                    val grade = doc.toObject(Grade::class.java)
                    grade?.id = doc.id
                    grade
                }
                callback(grades)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    // Add a student
    fun addStudent(student: Student, callback: (Boolean) -> Unit) {
        firestore.collection("students")
            .add(student)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    // Add a grade to a student
    fun addGrade(studentId: String, grade: Grade, callback: (Boolean) -> Unit) {
        firestore.collection("students")
            .document(studentId)
            .collection("grades")
            .add(grade)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    // Delete a grade
    fun deleteGrade(studentId: String, gradeId: String, callback: (Boolean) -> Unit) {
        firestore.collection("students")
            .document(studentId)
            .collection("grades")
            .document(gradeId)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    // Update a grade
    fun updateGrade(studentId: String, grade: Grade, callback: (Boolean) -> Unit) {
        if (grade.id.isEmpty()) {
            callback(false)
            return
        }
        firestore.collection("students")
            .document(studentId)
            .collection("grades")
            .document(grade.id)
            .set(grade)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
