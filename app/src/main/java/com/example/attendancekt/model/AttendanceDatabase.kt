package com.example.attendancekt.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.attendancekt.model.dao.AttendanceDao
import com.example.attendancekt.model.dao.MemberDao
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.Member

@Database(entities = [Member::class, Attendance::class], version = 1, exportSchema = false)
abstract class AttendanceDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun attendanceDao(): AttendanceDao
}