package umc.mobile.project.wishlist.GetLikePost

import com.google.gson.annotations.SerializedName
import umc.mobile.project.LikePost

data class LikePostGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<LikePost>
)
