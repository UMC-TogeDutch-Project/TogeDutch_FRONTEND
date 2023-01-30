package umc.mobile.project.announcement.Auth.PostPost

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part
import java.sql.Timestamp

data class PostRecordResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "post_id") val post_id : Int,
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

    @SerializedName(value =  "user_id") val user_id : Int,
    @Part @SerializedName(value =  "image") val image : MultipartBody.Part,

    @SerializedName(value =  "latitude") val latitude : Long,
    @SerializedName(value =  "longitude") val longitude : Long,
    @SerializedName(value =  "chatRoom_id") val chatRoom_id : Int,
    @SerializedName(value =  "category") val category : String
)
