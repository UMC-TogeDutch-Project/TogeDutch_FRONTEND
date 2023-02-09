package umc.mobile.project.ram.my_application_1

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class  Timestamp_to_SDF {
    fun convert(timestamp: Timestamp) : String{
        var year = timestamp.year
        var month = timestamp.month
        var day = timestamp.day
        var hour = timestamp.hours
        var minute = timestamp.minutes

//        var date : String = year.toString()+"년 "+ month.toString() + "월 " + day.toString() + "일 " + hour.toString() + "시 " + minute + "분"

        var sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분")

        var date = sdf.format(timestamp)

        return date
    }

    fun convert_only_time(timestamp: Timestamp) : String{
        var sdf = SimpleDateFormat("HH시 mm분")
        var date = sdf.format(timestamp)

        return date
    }

    fun timestamp_to_String(timestamp: Long) : String{
        //            2022-01-23T03:34:56.000+00:00
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss.000+00:00")
        var date = sdf.format(timestamp)

        return date
    }
}