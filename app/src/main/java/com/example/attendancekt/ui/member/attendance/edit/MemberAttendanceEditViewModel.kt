package com.example.attendancekt.ui.member.attendance.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.Member
import com.example.attendancekt.util.AppExecutors

class MemberAttendanceEditViewModel(application: Application) : AndroidViewModel(application) {

    private val attendanceRepo = ServiceLocator.getInstance(application).attendanceRepo
    private val memberRepo = ServiceLocator.getInstance(application).memberRepo()


    val members: LiveData<List<Member>> by lazy { memberRepo.getAllMember() }

    var attendanceId = MutableLiveData<Long>()

    var attendance: LiveData<Attendance> = Transformations.switchMap(attendanceId) {
        if (it > 0) {
            attendanceRepo.getAttendace(it)
        } else {
           val liveData =  MutableLiveData<Attendance>()
            liveData.value = Attendance()
            liveData
        }
    }

    fun save() {
        AppExecutors().diskIO().execute {
            attendance.value?.also { attendanceRepo.save(it) }
        }
    }
}