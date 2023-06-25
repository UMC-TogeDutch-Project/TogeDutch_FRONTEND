package umc.mobile.project.ram.my_application_1

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

const val KR_TIME_DIFF = 9 * 60 * 1000

class  Timestamp_to_SDF {
    fun convert(timestamp: Timestamp) : String{
        var year = timestamp.year
        var month = timestamp.month
        var day = timestamp.day
        var hour = timestamp.hours
        var minute = timestamp.minutes

//        var date : String = year.toString()+"년 "+ month.toString() + "월 " + day.toString() + "일 " + hour.toString() + "시 " + minute + "분"

        var sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분")
//        sdf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        var date = sdf.format(timestamp)

        return date
    }

    fun convert_only_time(timestamp: Timestamp) : String{
        // 9시간 빼줘야함,,,,나도 힘들다,,
        var hour = timestamp.hours
        var minute = timestamp.minutes

        var hour_cal = hour - 9 // 현재 오전 3시일 때
        if(hour_cal < 0) // -6이 나오니까
            hour = 24 + hour_cal // 24데 더해서 18시가 나오게
        else
            hour = hour_cal // 계산한 거 그대로 넣어주기

        if(hour < 12){
            return "오전 ${hour}시 ${minute}분"
        }
        else if(hour == 12){
            return "오후 ${hour}시 ${minute}분"
        }
        else{
            return "오후 ${hour - 12}시 ${minute}분"
        }
    }

    fun timestamp_to_String(timestamp: Long) : String{
        //            2022-01-23T03:34:56.000+00:00
        var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss.000+00:00")
//        sdf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        var date = sdf.format(timestamp)

        return date
    }
}