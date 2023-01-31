package umc.mobile.project.announcement.Auth.PostImminentGet

import Post
import com.google.gson.annotations.SerializedName


data class PostImminentResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<Post>
)
