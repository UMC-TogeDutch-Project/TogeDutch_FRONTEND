package umc.mobile.project.ram.my_application_1

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Timestamp_to_SDF {
    fun convert(timestamp: Timestamp) : String{
        var hour = timestamp.hours
        var minute = timestamp.minutes

        var date : String = hour.toString() + "시 " + minute + "분"

//        var sdf = SimpleDateFormat("HH시 mm분", Locale.KOREA)
//
//        var date = sdf.parse(timestamp.toString())

        return date
    }
}