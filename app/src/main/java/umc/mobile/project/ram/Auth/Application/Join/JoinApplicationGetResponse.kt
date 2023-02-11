package umc.mobile.project.ram.Auth.Application.Join

import com.google.gson.annotations.SerializedName
import umc.mobile.project.ram.Auth.Application.ViewUpload.ApplicationGet

data class JoinApplicationGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<ApplicationGet>
)