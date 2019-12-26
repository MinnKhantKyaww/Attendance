package com.example.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.Member
import com.example.attendancekt.model.repo.MemberRepo
import com.example.attendancekt.util.AppExecutors

class MemberViewModel : AndroidViewModel {

    private var memberRepo: MemberRepo? = null

    val members: LiveData<List<Member>> by lazy {
        memberRepo?.getAllMember()!!
    }

    constructor(application: Application) : super(application) {
        this.memberRepo = ServiceLocator.getInstance(application).memberRepo()
    }


}

