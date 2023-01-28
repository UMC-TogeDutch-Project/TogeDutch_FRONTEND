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
    var category : String,
    var heart_isSelected : Boolean // wishListRVAdapter 에서 색 바뀌는 것 때문에 임의로 설정한 거! 나중에 이거만 Serialized 제외하기
)