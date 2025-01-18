package com.example.studentsapp.model
import java.io.Serializable


data class Student(
    var name: String,
    var id: String,
    var phone: String,
    var address: String,
    val avatarUrl: String,
    var isChecked: Boolean
): Serializable