package umc.mobile.project.mypage.profile.emotionStatus

import com.google.gson.annotations.SerializedName

data class EmotionStatusGet (
    @SerializedName(value = "post_id") val post_id: Int,
    @SerializedName(value = "avg") val avg : Double
)