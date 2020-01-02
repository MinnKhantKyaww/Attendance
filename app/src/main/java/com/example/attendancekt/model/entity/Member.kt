package com.example.attendancekt.model.entity

import androidx.annotation.ColorLong
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                   var name: String = "",
                   var age: Int = 0,
                   var email: String = "",
                   var phone: String = "",
                   var photo: String = "",
                   var barcode: String = "") {

    override fun toString(): String {
        return name
    }
}