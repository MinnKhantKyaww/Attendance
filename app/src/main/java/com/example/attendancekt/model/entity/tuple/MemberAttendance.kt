package com.example.attendancekt.model.entity.tuple

import androidx.room.ColumnInfo
import org.joda.time.DateTime

data class MemberAttendance(
    var id: Long = 0,
    var name: String = "",
    @ColumnInfo(name = "event_time")
    var eventTime: DateTime = DateTime.now()
)