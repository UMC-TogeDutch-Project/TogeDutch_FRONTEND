package umc.mobile.project.ram.Auth.Review

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

class ReviewPostResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Integer
)
