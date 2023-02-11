package umc.mobile.project.ram.Auth.Review

import com.google.gson.annotations.SerializedName

data class ReviewPostRequest (
    @SerializedName(value =  "emotionStatus") val emotionStatus : Int,
    @SerializedName(value =  "content") val content : String
)