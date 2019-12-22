package com.example.attendancekt.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.attendancekt.model.entity.Member

@Dao
interface MemberDao: CudDao<Member> {

    @Query("SELECT * FROM MEMBER")
    fun getAllMember(): LiveData<List<Member>>

    @Query("SELECT * FROM MEMBER WHERE id = :id LIMIT 1")
    fun getMember(id: Int): LiveData<Member>
}