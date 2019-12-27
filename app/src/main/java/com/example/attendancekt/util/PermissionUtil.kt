package com.example.attendancekt.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionUtil {

    companion object {
        val PERMISSION_CAMERA = 100
        fun hasCameraPermission(fragment: Fragment): Boolean {
            if(fragment.context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) } != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
                return false
                }
            return true
        }

    }
}