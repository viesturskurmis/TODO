package lv.vie.mobile.rtu_android

import android.app.Application
import androidx.room.Room

class App : Application() {
    val db by lazy {
        Room.databaseBuilder(this, TodoDatabase::class.java, "todo-db")
            .allowMainThreadQueries()
            .build()
    }
}