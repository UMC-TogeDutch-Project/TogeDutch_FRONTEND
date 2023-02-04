package umc.mobile.project.announcement.Auth.ApplyPost

import com.google.gson.annotations.SerializedName

data class ApplyRecord(
    @SerializedName(value =  "post_id") val post_id : String,

//    @SerializedName(value =  "chatRoom_id") val chatRoom_id : String,
)
