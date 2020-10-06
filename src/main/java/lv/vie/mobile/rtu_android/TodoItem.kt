package lv.vie.mobile.rtu_android

import androidx.room.*

@Entity(tableName = "todo_list")
data class TodoItem(
    val name: String,
    val priority: Int,
    val desc: String,
    val type: Int,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface TodoItemDao {
    @Insert
    fun insertAll(vararg items: TodoItem): List<Long>
    @Query("SELECT * FROM todo_list")
    fun getAll(): List<TodoItem>
    @Query("SELECT * FROM todo_list WHERE uid == :itemId")
    fun getItemById(itemId: Long): TodoItem
    @Update
    fun update(item: TodoItem)
    @Delete
    fun delete(item: TodoItem)
}