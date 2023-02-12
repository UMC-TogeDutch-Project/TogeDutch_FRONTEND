package umc.mobile.project.mypage.profile.Review

import com.google.gson.annotations.SerializedName
import umc.mobile.project.mypage.profile.ReviewGet

data class ReviewGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<ReviewGet>
)