package lv.vie.mobile.rtu_android

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoItemRecyclerAdapter(private val listener: AdapterListener, private val items: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoItemRecyclerAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        var bg_color: String = "#333333"
        var text_color: String = "#000000"
        val context = holder.itemView.context
        holder.itemView.TodoName.text = item.name

        when (item.priority) {
            0 -> holder.itemView.TodoPriority.text = context.resources.getStringArray(R.array.todo_priorities_array)[0]  //"Varbūt"
            1 -> holder.itemView.TodoPriority.text = context.resources.getStringArray(R.array.todo_priorities_array)[1]  //"Kaut kad"
            2 -> holder.itemView.TodoPriority.text = context.resources.getStringArray(R.array.todo_priorities_array)[2]  //"Normālā ritmā"
            3 -> holder.itemView.TodoPriority.text = context.resources.getStringArray(R.array.todo_priorities_array)[3]  //"Steidzami"
            else -> holder.itemView.TodoPriority.text = context.resources.getStringArray(R.array.todo_priorities_array)[0]  //"Normālā ritmā"
        }

        when (item.type) {
            0 -> holder.itemView.TodoType.text = context.resources.getStringArray(R.array.todo_types_array)[0]    //"Darbs"
            1 -> holder.itemView.TodoType.text = context.resources.getStringArray(R.array.todo_types_array)[1]    //"Privātās lietas"
            2 -> holder.itemView.TodoType.text = context.resources.getStringArray(R.array.todo_types_array)[2]    //"Mācības"
            3 ->  holder.itemView.TodoType.text = context.resources.getStringArray(R.array.todo_types_array)[3]    //"Cits"
            else ->  holder.itemView.TodoType.text = context.resources.getStringArray(R.array.todo_types_array)[0]    //"Darbs"
        }

        when (item.type) {
            0 -> bg_color = "#FFAA2A"  //"Darbs"
            1 -> bg_color = "#D4FF7F"  // "Privātās lietas"
            2 -> bg_color = "#7FD4FF"  // "Mācības"
            3 -> {bg_color = "#555555"; text_color = "#FFFFFF"}   //"Cits"
            else -> bg_color = "#ffccff"
        }

        holder.itemView.setBackgroundColor(Color.parseColor(bg_color))
        holder.itemView.TodoName.setTextColor(Color.parseColor(text_color))
        holder.itemView.TodoType.setTextColor(Color.parseColor(text_color))

        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.TodoRemove.setOnClickListener {
           listener.removeClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}