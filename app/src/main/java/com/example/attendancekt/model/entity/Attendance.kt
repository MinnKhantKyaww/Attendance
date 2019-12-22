package com.example.attendancekt.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attendance(@PrimaryKey(autoGenerate = true) val id: Long) {

}