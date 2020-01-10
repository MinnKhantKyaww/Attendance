package com.example.attendancekt.ui.member.attendance.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.Attendance
import com.example.attendancekt.model.entity.Member

class MemberAttendanceEditViewModel(application: Application) : AndroidViewModel(application) {

    private val attendanceRepo = ServiceLocator.getInstance(application).attendanceRepo
    private val memberRepo = ServiceLocator.getInstance(application).memberRepo()

    val attendanceId = MutableLiveData<Long>()
    val memberId = MutableLiveData<Int>()

    var operation = MutableLiveData<Boolean>()

    val members: LiveData<List<Member>> by lazy { memberRepo.getAllMember() }

    val member: LiveData<Member> = Transformations.switchMap(memberId) { memberRepo.getMember(it) }

    val attendance: LiveData<Attendance> = Transformations.switchMap(attendanceId) {
        if (it > 0) {
            attendanceRepo.getAttendace(it)
        } else {
            val liveData = MutableLiveData<Attendance>()
            liveData.value = Attendance()
            liveData

        }
    }

    fun save() {
        attendance.value?.also {
            try {
                attendanceRepo.save(it)
                operation.postValue(true)
            }catch (e: Exception) {
                operation.postValue(false)
            }

        }
    }
}