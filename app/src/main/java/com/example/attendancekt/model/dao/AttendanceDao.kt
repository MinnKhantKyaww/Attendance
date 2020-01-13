package com.example.attendancekt.model.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.tuple.MemberAttendance

@Dao
interface AttendanceDao: CudDao<Attendance> {

    @Query("SELECT * FROM Attendance WHERE id = :id LIMIT 1")
    fun getAttendace(id: Long): LiveData<Attendance>

    @Query("SELECT a.id, m.name, a.event_time, a.status  FROM Attendance a " +
            "LEFT JOIN Member m ON a.member_id = m.id " +
            "ORDER BY a.event_time DESC")
    fun getAll(): DataSource.Factory<Int, MemberAttendance>

}