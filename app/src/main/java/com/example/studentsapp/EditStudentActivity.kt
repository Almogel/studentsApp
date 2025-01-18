package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var imageViewProfile: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var checkBoxIsChecked: CheckBox
    private lateinit var buttonCancel: Button
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        imageViewProfile = findViewById(R.id.imageViewProfile)
        editTextName = findViewById(R.id.editTextName)
        editTextId = findViewById(R.id.editTextId)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextAddress = findViewById(R.id.editTextAddress)
        checkBoxIsChecked = findViewById(R.id.checkBoxIsChecked)
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonSave = findViewById(R.id.buttonSave)
        buttonDelete = findViewById(R.id.buttonDelete)

        student = intent.getSerializableExtra("student") as? Student
        student?.let {
            editTextName.setText(it.name)
            editTextId.setText(it.id)
            editTextPhone.setText(it.phone)
            editTextAddress.setText(it.address)
            checkBoxIsChecked.isChecked = it.isChecked
        }

        buttonSave.setOnClickListener {
            student?.apply {
                name = editTextName.text.toString()
                id = editTextId.text.toString()
                phone = editTextPhone.text.toString()
                address = editTextAddress.text.toString()
                isChecked = checkBoxIsChecked.isChecked

                val index = Model.shared.students.indexOfFirst { it.id == this.id }
                if (index != -1) {
                    Model.shared.students[index] = this
                }
            }

            val resultIntent = Intent()
            resultIntent.putExtra("updatedStudent", student)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        buttonCancel.setOnClickListener {
            finish()
        }

        buttonDelete.setOnClickListener {
            student?.let { studentToDelete ->
                val index = Model.shared.students.indexOfFirst { it.id == studentToDelete.id }
                if (index != -1) {
                    Model.shared.students.removeAt(index)
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}