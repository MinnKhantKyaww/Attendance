package com.example.attendancekt.util

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.example.attendancekt.model.entity.Member
import kotlin.math.abs

class BindingUtil {

    /*@BindingAdapter("android:text")
    fun setNumber(textView: TextView, value: Long) {
        textView.text = value.toString()
    }

    @BindingAdapter("android:text")
    fun setNumber(editText: EditText, value: Int) {
        editText.setText(value.toString())
    }

    @InverseBindingAdapter(attribute = "android:text")
    fun getNumber(editText: EditText): Int {
        val value: String = editText.text.toString()
        return if(value.isEmpty()) 0 else value.toInt()
    }*/

    /*fun setImageUri(imageView: ImageView, imageFilePath: String) {
        if(imageFilePath != null && !imageFilePath.isEmpty()) {
            imageView.setImageURI(Uri.parse(imageFilePath))
        }
    }*/

    @BindingAdapter("visibile")
    fun View.setVisibile(member: Member) {
        visibility = if(member.id > 0) View.VISIBLE else View.INVISIBLE
    }
}