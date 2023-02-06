package umc.mobile.project.ram.Auth.Post.PUTRetouch

import Post
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PutRetouchResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Post?
)
data class Request_put(
    @SerializedName(value =  "title") val title : String,
    @SerializedName(value =  "url") val url : String,
    @SerializedName(value =  "delivery_tips") val delivery_tips : Int,
    @SerializedName(value =  "minimum") val minimum : Int,
    @SerializedName(value =  "order_time") val order_time : String,
    @SerializedName(value =  "num_of_recruits") val num_of_recruits : Int,
    @SerializedName(value =  "recruited_num") val recruited_num : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
)

