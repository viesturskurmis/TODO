package lv.vie.mobile.rtu_android

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_main.*

class FormActivity : AppCompatActivity() {

    private val db by lazy {
        (application as App).db
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editBackButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Type dropdown
        val typeSpinner = findViewById<Spinner>(R.id.editTypeDropDown)
        val typeDropDownItems: Array<String> = resources.getStringArray(R.array.todo_types_array)
        val adapterSpinnerType = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            typeDropDownItems
        )
        typeSpinner.setAdapter(adapterSpinnerType)

        // Priority dropdown
        val prioritySpinner = findViewById<Spinner>(R.id.editPriorityDropDown)
        val priorityDropDownItems: Array<String> = resources.getStringArray(R.array.todo_priorities_array)
        val adapterSpinnerPriority = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            priorityDropDownItems
        )
        prioritySpinner.setAdapter(adapterSpinnerPriority)

        val itemUid=intent.getLongExtra("itemUid",44)
        val item = db.TodoItem().getItemById(itemUid)

        // Show values in form
        editTodoTitle.setText(item.name)
        editTodoDesc.setText(item.desc)
        editTypeDropDown.setSelection(item.type)
        editPriorityDropDown.setSelection(item.priority)

        editSaveButton.setOnClickListener { updateItem(itemUid) }
        shareButton.setOnClickListener { shareItem() }
    }

    private fun shareItem(){

        val shareText = "TODO [" + editTypeDropDown.selectedItem.toString() + "] - " + editTodoTitle.text.toString() + " " + editTodoDesc.text.toString()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun updateItem(itemUid: Long) {

        if (editTodoTitle.text.isEmpty()){
            Toast.makeText(this, getString(R.string.toast_no_title_provided), Toast.LENGTH_SHORT).show()
        }

        else {

            val selectedTypeText = editTypeDropDown.selectedItem.toString()
            var selectedTypeId = 0
            when (editTypeDropDown.selectedItem.toString()) {
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

            val selectedPriorityText = editPriorityDropDown.selectedItem.toString()
            var selectedPriorityId = 0
            when (editPriorityDropDown.selectedItem.toString()) {
                "Varbūt" -> selectedPriorityId = 0
                "Kaut kad" -> selectedPriorityId = 1
                "Normālā ritmā" -> selectedPriorityId = 2
                "Steidzami" -> selectedPriorityId = 3
                "Nice to have" -> selectedPriorityId = 0
                "Sometime" -> selectedPriorityId = 1
                "Normal" -> selectedPriorityId = 2
                "Important" -> selectedPriorityId = 3
                else -> selectedPriorityId = 0
            }

            val item = TodoItem(
                editTodoTitle.text.toString(),
                selectedPriorityId,
                editTodoDesc.text.toString(),
                selectedTypeId,
                itemUid
            )

            db.TodoItem().update(item)
            Toast.makeText(this, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
        }
    }
}