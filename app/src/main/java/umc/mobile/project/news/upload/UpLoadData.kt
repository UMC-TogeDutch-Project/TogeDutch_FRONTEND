package umc.mobile.project.news.upload

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class UpLoadData(
    @SerializedName(value =  "post_id") val post_id : Int,
    @SerializedName(value =  "title") val title : String,
    @SerializedName(value =  "url") val url : String,
    @SerializedName(value =  "delivery_tips") val delivery_tips : Int,
    @SerializedName(value =  "minimum") val minimum : Int,
    @SerializedName(value =  "order_time") val order_time : String,
    @SerializedName(value =  "num_of_recruits") val num_of_recruits : Int,
    @SerializedName(value =  "recruited_num") val recruited_num : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "created_at") val created_at : Timestamp,
    @SerializedName(value =  "updated_at") val updated_at : Timestamp?,
    @SerializedName(value =  "user_id") val user_id : Int,
    @SerializedName(value =  "image") val image : String?,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
    @SerializedName(value =  "chatRoom_id") val chatRoom_id : Int,
    @SerializedName(value =  "category") val category : String

)
