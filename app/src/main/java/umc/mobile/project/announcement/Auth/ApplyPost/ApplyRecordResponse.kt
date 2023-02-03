package umc.mobile.project.announcement.Auth.ApplyPost

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ApplyRecordResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "application_id") val application_id : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "post_id") val post_id : Int,
    @SerializedName(value =  "user_id") val user_id : Int,
    @SerializedName(value =  "chatRoom_id") val chatRoom_id : Int
)
