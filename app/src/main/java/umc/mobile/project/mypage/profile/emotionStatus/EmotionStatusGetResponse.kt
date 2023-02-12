package umc.mobile.project.mypage.profile.emotionStatus

import com.google.gson.annotations.SerializedName

data class EmotionStatusGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : EmotionStatusGet
)