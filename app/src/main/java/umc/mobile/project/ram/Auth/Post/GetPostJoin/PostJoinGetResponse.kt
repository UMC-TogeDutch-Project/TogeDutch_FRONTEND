package umc.mobile.project.ram.Auth.Post.GetPostJoin

import com.google.gson.annotations.SerializedName
import Post


data class PostJoinGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<Post>
)
