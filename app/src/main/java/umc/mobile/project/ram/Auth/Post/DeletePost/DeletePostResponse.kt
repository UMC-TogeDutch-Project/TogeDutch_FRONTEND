package umc.mobile.project.ram.Auth.Post.DeletePost

import com.google.gson.annotations.SerializedName
import umc.mobile.project.ram.Auth.Post.PUTRetouch.Result
import java.sql.Timestamp

data class DeletePostResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Int?
)


