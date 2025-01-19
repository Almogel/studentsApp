package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.adapter.OnItemClickListener
import com.example.studentsapp.adapter.StudentRecyclerAdapter
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentsRecyclerViewActivity : AppCompatActivity() {

    private lateinit var students: MutableList<Student>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerAdapter

    private val studentDetailsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedStudent = result.data?.getSerializableExtra("updatedStudent") as? Student
                updatedStudent?.let {
                    val index = students.indexOfFirst { student -> student.id == it.id }
                    if (index != -1) {
                        // Update shared list
                        Model.shared.students[index] = it

                        // Update local list and notify adapter
                        students[index] = it
                        adapter.notifyItemChanged(index)
                    }
                }
            }
        }

    private val addStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val newStudent = result.data?.getSerializableExtra("newStudent") as? Student
                newStudent?.let {
                    // Add to shared list
                    Model.shared.students.add(it)

                    // Add to local list and notify adapter
                    students.add(it)
                    adapter.notifyItemInserted(students.size - 1)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_recycler_view)

        // Set the title of the ActionBar
        supportActionBar?.title = "Students List"

        students = Model.shared.students // Synchronize with shared list

        recyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerAdapter(students)
        recyclerView.adapter = adapter

        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedStudent = students[position]
                val intent = Intent(this@StudentsRecyclerViewActivity, StudentDetailsActivity::class.java)
                intent.putExtra("student", selectedStudent)
                studentDetailsLauncher.launch(intent)
            }
        }

        val fab: FloatingActionButton = findViewById(R.id.add_student_fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }
    }
}