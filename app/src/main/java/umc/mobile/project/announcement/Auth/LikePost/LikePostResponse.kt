package umc.mobile.project.announcement.Auth.LikePost

import com.google.gson.annotations.SerializedName

data class LikePostResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Result?
)

data class Result(
    @SerializedName(value =  "likeIdx") val likeIdx : Int,
    @SerializedName(value =  "postIdx") val postIdx : Int,
    @SerializedName(value =  "post_User_userIdx") val post_User_userIdx : Int,
    @SerializedName(value =  "like_userIdx") val like_userIdx : Int

)
