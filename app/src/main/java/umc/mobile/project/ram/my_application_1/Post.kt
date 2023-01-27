package umc.mobile.project.ram.my_application_1


data class Post(
    var post_id : Int,
    var title : String,
    var url : String,
    var delivery_tips : Int,
    var minimum : Int,
    var order_time : String,
    var num_of_recruits : Int,
    var recruited_num : Int,
    var status : String,
    var created_at : String,
    var updated_at : String? = null,
    var user_id : Int,
    var Latitude : Double,
    var longitude : Double
)