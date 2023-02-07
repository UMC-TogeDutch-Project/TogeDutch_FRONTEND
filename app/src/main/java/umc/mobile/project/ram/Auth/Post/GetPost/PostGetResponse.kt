package umc.mobile.project.ram.Auth.Post.GetPost

import com.google.gson.annotations.SerializedName
import Post


data class PostGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Post
)
