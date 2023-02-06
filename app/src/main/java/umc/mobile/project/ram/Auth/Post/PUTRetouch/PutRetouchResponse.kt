package umc.mobile.project.ram.Auth.Post.PUTRetouch

import Post
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PutRetouchResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Post?
)

