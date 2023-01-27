package umc.mobile.project.wishlist

import java.sql.Timestamp

data class WishApplication(
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
    var Latitude : Double,
    var longitude : Double,
    var chatRoom_id : Int,
    var category : String
)