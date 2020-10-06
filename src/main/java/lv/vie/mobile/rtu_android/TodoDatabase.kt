package lv.vie.mobile.rtu_android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TodoItem::class])
abstract class TodoDatabase : RoomDatabase() {
    abstract fun TodoItem(): TodoItemDao
}