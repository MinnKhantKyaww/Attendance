package com.example.attendancekt.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class FileUtil {

    companion object{
        @Throws(Exception::class)
        fun writeImage(context: Context, uri: Uri, imageFIle: File): Bitmap? {

            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri),
                null, options)

            val REQUIRED_SIZE = 240

            var width_tmp: Int = options.outWidth
            var height_tmp = options.outHeight

            var scale = 1
            while(width_tmp / 2 >= REQUIRED_SIZE || height_tmp / 2 >= REQUIRED_SIZE) {

                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }
            val scaledOpts = BitmapFactory.Options()
            scaledOpts.inSampleSize = scale

            var bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, scaledOpts)

            var fos = FileOutputStream(imageFIle)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos)

            return bitmap
        }
    }
}