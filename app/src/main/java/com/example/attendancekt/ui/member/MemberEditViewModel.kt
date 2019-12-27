package com.example.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.attendancekt.ServiceLocator
import com.example.attendancekt.model.entity.Member
import com.example.attendancekt.model.repo.MemberRepo
import com.example.attendancekt.util.AppExecutors

class MemberEditViewModel: AndroidViewModel {

    private var memberRepo: MemberRepo? = null

    var members: MutableLiveData<Member> = MutableLiveData()

    var operation: MutableLiveData<Boolean> = MutableLiveData()

    constructor(application: Application) : super(application) {
        this.memberRepo = ServiceLocator.getInstance(application).memberRepo()
    }

    fun init(id: Int) {
       AppExecutors().diskIO().execute {
            var member = memberRepo?.getMemberSync(id)
            if (member != null) members.postValue(member) else members.postValue(Member())
        }
    }

    fun save() {
        AppExecutors().diskIO().execute {
            try {
                memberRepo?.save(members.value!!)
                operation.postValue(true)
            }catch (e: Exception) {
                operation.postValue(false)
            }

        }
    }
}