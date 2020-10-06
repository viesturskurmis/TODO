package lv.vie.mobile.rtu_android

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterListener {

    private val db by lazy {
        (application as App).db
    }

    private val items = mutableListOf<TodoItem>()

    private lateinit var adapter: TodoItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items.addAll(db.TodoItem().getAll())

        adapter = TodoItemRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        resortItems()

        mainButtonAdd.setOnClickListener { appendItem() }

        val spinner = findViewById<Spinner>(R.id.typeDropDown)
        val dropDownItems: Array<String> = resources.getStringArray(R.array.todo_types_array)
        val adapterSpinnerType = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            dropDownItems
        )
        spinner.setAdapter(adapterSpinnerType)
    }

    private fun appendItem() {

        if (inputTodoTitle.text.isEmpty()){
            Toast.makeText(this, getString(R.string.toast_no_title_provided), Toast.LENGTH_SHORT).show()
        }

        else {

           val selectedTypeText = typeDropDown.selectedItem.toString()
           var selectedTypeId = 0

            when (typeDropDown.selectedItem.toString()) {
                "Darbs" -> selectedTypeId = 0
                "Job" -> selectedTypeId = 0
                "Privātās lietas" -> selectedTypeId = 1
                "Private" -> selectedTypeId = 1
                "Mācības" -> selectedTypeId = 2
                "Education" -> selectedTypeId = 2
                "Cits" -> selectedTypeId = 3
                "Other" -> selectedTypeId = 3
                else -> selectedTypeId = 0
            }

            val item = TodoItem(
                inputTodoTitle.text.toString(),
                2,
                "",
                selectedTypeId
            )
            item.uid = db.TodoItem().insertAll(item).first()

            items.add(item)
            resortItems()
            inputTodoTitle.text.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun resortItems() {
        items.sortByDescending {it.uid}
    }

    override fun removeClicked(item: TodoItem) {
        db.TodoItem().delete(item)
    }

    override fun itemClicked(item: TodoItem) {
        val intentToForm = Intent(this, FormActivity::class.java)
        intentToForm.putExtra("itemUid", item.uid)
        startActivity(intentToForm)
    }
}

interface AdapterListener {

    fun removeClicked(item: TodoItem)
    fun itemClicked(item: TodoItem)
}


