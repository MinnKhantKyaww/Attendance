package com.example.attendancekt.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.attendancekt.model.dao.AttendanceDao
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.tuple.MemberAttendance

class AttendanceRepo(private val dao: AttendanceDao) {

    fun save(attendace: Attendance) {
        if(attendace.id > 0) {
            dao.update(attendace)
        } else {
            dao.insert(attendace)
        }
    }

    fun getAttendace(id: Long) = dao.getAttendace(id)

    fun getAll(): LiveData<PagedList<MemberAttendance>> {
        return LivePagedListBuilder(dao.getAll(), 3).build()
    }

}