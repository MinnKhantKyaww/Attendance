package com.example.attendancekt.util

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.attendancekt.model.entity.Member
import kotlin.math.abs

class BindingUtil {

    companion object {
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

        @BindingAdapter("path")
        fun setImageUri(imageView: ImageView, imageFilePath: String) {
            if (imageFilePath != null && imageFilePath.isNotEmpty()) {
                imageView.setImageURI(Uri.parse(imageFilePath))
            }
        }

        @BindingAdapter("visibility")
        fun View.setVisibile(member: MutableLiveData<Member?>) {
            visibility = if (member.value?.id!! > 0) {
                View.VISIBLE
            } else View.INVISIBLE
        }

    }
}