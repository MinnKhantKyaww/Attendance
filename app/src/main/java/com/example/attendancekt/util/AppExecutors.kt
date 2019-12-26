package com.example.attendancekt.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors(

    private var diskIO: Executor,
    private var mainThread: Executor) {


    constructor(): this(
        Executors.newSingleThreadExecutor(),
        MainThreadExecutor()
    )

    public fun diskIO(): Executor {
        if(diskIO == null) {
            diskIO = MainThreadExecutor()
        }
        return diskIO
    }

    public fun mainThread(): Executor {
        if(mainThread == null) {
            mainThread = MainThreadExecutor()
        }
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}

