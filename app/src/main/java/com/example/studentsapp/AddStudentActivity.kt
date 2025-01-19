package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        supportActionBar?.title = "Add Student"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.add_student_save_button)
        val cancelButton: Button = findViewById(R.id.add_student_cancel_button)

        val nameTextField: EditText = findViewById(R.id.add_student_name_text_field)
        val idTextField: EditText = findViewById(R.id.add_student_id_text_field)
        val saveTextField: TextView = findViewById(R.id.add_student_success_saved_text_view)
        val phoneTextField: EditText = findViewById(R.id.add_student_phone_text_field)
        val addressTextField: EditText = findViewById(R.id.add_student_address_text_field)
        val isCheckedCheckBox: CheckBox = findViewById(R.id.add_student_activity_checked_checkbox)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val name = nameTextField.text.toString()
            val id = idTextField.text.toString()
            val phone = phoneTextField.text.toString()
            val address = addressTextField.text.toString()
            val isChecked = isCheckedCheckBox.isChecked

            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                saveTextField.text = "Please fill in all fields"
            } else {
                val newStudent = Student(
                    name = name,
                    id = id,
                    avatarUrl = "",
                    isChecked = isChecked,
                    phone = phone,
                    address = address
                )

                Model.shared.students.add(newStudent)

                val intent = Intent(this, StudentsRecyclerViewActivity::class.java)
                startActivity(intent)

                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}