package umc.mobile.project.announcement.Auth.PostPost

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part
import java.sql.Timestamp
import java.time.Instant

data class PostRecord(
    @SerializedName(value =  "title") val title : String,
    @SerializedName(value =  "url") val url : String,
    @SerializedName(value =  "delivery_tips") val delivery_tips : Int,
    @SerializedName(value =  "minimum") val minimum : Int,
    @SerializedName(value =  "order_time") val order_time : Timestamp,
    @SerializedName(value =  "num_of_recruits") val num_of_recruits : Int,
    @SerializedName(value =  "recruited_num") val recruited_num : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
    @SerializedName(value =  "category") val category : String,
//    @Part @SerializedName(value =  "image") val image : MultipartBody.Part?
)
