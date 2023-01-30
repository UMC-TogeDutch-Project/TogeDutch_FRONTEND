package umc.mobile.project.ram.my_application_1

import java.sql.Timestamp
import java.text.SimpleDateFormat


data class Post(
    var post_id : Int,
    var title : String,
    var url : String,
    var delivery_tips : Int,
    var minimum : Int,
    var order_time : Timestamp,
    var num_of_recruits : Int,
    var recruited_num : Int,
    var status : String,
    var created_at : String,
    var updated_at : String?,
    var user_id : Int,
    var Latitude : Double,
    var longitude : Double
)
