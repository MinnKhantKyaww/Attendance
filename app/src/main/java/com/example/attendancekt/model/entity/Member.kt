package com.example.attendancekt.model.entity

import androidx.annotation.ColorLong
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(@PrimaryKey(autoGenerate = true) val id: Int,
                   val name: String,
                   val age: Int,
                   val email: String,
                   val phone: String,
                   val photo: String,
                   val barcode: String) {

}