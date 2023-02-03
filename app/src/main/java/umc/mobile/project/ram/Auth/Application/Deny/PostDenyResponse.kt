package umc.mobile.project.ram.Auth.Application.Deny

import com.google.gson.annotations.SerializedName

data class PostDenyResponse (
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
