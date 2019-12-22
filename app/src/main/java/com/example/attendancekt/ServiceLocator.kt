package com.example.attendancekt

import android.content.Context
import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import com.example.attendancekt.model.AttendanceDatabase
import com.example.attendancekt.model.repo.MemberRepo

interface ServiceLocator {

    companion object {
        lateinit var instance: ServiceLocator

        fun getInstance(context: Context): ServiceLocator = if (!this::instance.isInitialized) {
            instance = DefaultServiceLocator(context)
            instance
        } else {
            instance
        }
    }

    fun memberRepo(): MemberRepo

    class DefaultServiceLocator(private val context: Context): ServiceLocator {
        val database = Room.inMemoryDatabaseBuilder(context, AttendanceDatabase::class.java)
            .allowMainThreadQueries().build()

        override fun memberRepo(): MemberRepo = MemberRepo(database.memberDao())

    }

}