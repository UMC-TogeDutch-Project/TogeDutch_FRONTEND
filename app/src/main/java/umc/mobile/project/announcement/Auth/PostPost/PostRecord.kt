package umc.mobile.project.announcement.Auth.PostPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PostRecord(
    @SerializedName(value =  "title") val title : String,
    @SerializedName(value =  "url") val url : String,
    @SerializedName(value =  "delivery_tips") val delivery_tips : Int,
    @SerializedName(value =  "minimum") val minimum : Int,
    @SerializedName(value =  "order_time") val order_time : Timestamp,
    @SerializedName(value =  "num_of_recruits") val num_of_recruits : Int,
    @SerializedName(value =  "recruited_num") val recruited_num : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "created_at") val created_at : Timestamp,
    @SerializedName(value =  "updated_at") val updated_at : Timestamp,
    @SerializedName(value =  "latitude") val latitude : Long,
    @SerializedName(value =  "longitude") val longitude : Long,
    @SerializedName(value =  "chatRoom_id") val chatRoom_id : Int,
    @SerializedName(value =  "category") val category : String,
)
