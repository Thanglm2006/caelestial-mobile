package com.example.crudfirebase

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {

    private val db = FirebaseFirestore.getInstance()
    private val col = db.collection("students")

    fun add(student: Student) {
        val doc = db.collection("students").document()
        student.id = doc.id
        doc.set(student)
    }

    fun getAll(callback: (List<Student>) -> Unit) {
        db.collection("students")
            .get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<Student>()

                for (doc in result) {
                    val student = doc.toObject(Student::class.java)

                    // 🔥 FIX QUAN TRỌNG
                    student.id = doc.id

                    list.add(student)
                }

                callback(list)
            }
    }

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun update(student: Student) {
        if (student.id.isBlank()) {
            println("ID rỗng -> không update")
            return
        }

        db.collection("students")
            .document(student.id)
            .set(student)
    }
}