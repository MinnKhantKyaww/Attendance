package com.example.attendancekt.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.attendancekt.model.dao.AttendanceDao
import com.example.attendancekt.model.dao.MemberDao
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.Member
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime

@Database(entities = [Member::class, Attendance::class], version = 1, exportSchema = false)
@TypeConverters(com.example.attendancekt.model.TypeConverters::class)
abstract class AttendanceDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun attendanceDao(): AttendanceDao
}

class TypeConverters {

    companion object {

        @JvmStatic
        @TypeConverter
        fun getLocalDateTime(timeStamp: Long): DateTime {
            return DateTime(timeStamp, DateTimeZone.getDefault())
        }

        @JvmStatic
        @TypeConverter
        fun setLocaDateTime(dataTime: DateTime): Long {
            return dataTime.millis
        }
    }
}