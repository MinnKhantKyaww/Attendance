package com.example.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.Member
import com.example.attendancekt.model.repo.MemberRepo
import com.example.attendancekt.util.AppExecutors

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var memberRepo: MemberRepo

    val members: LiveData<List<Member>> by lazy {
        memberRepo.getAllMember()
    }

    init {
        this.memberRepo = ServiceLocator.getInstance(application).memberRepo()
    }

}

