package umc.mobile.project.wishlist.GetLikePost

import Post
import com.google.gson.annotations.SerializedName

data class LikePostGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<Post>
)
