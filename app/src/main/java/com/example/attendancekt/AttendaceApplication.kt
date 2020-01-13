package com.example.attendancekt

import android.app.Application

class AttendaceApplication: Application() {

    companion object {
        lateinit var currentUser: String
    }
}