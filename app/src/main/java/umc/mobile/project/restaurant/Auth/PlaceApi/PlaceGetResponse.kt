package umc.mobile.project.restaurant.Auth.PlaceApi

import com.google.gson.annotations.SerializedName
import Post


data class PlaceGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Post
)
