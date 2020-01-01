package com.example.attendancekt.ui.member.attendance

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.tuple.MemberAttendance
import com.example.attendancekt.model.repo.AttendanceRepo

class MemberAttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val attendanceRepo = ServiceLocator.getInstance(application).attendanceRepo

    val attedance: LiveData<PagedList<MemberAttendance>> by lazy { attendanceRepo.getAll() }
}