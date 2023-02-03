package umc.mobile.project.ram.Auth.Application.ViewUpload

import com.google.gson.annotations.SerializedName

data class ApplicationGet(
    @SerializedName(value =  "application_id") val application_id : Int,
    @SerializedName(value =  "status") val status : String,
    @SerializedName(value =  "post_id") val post_id : Int,
    @SerializedName(value =  "user_id") val user_id : Int,
    @SerializedName(value =  "chatRoom_id") val chatRoom_id : Int
)
