package umc.mobile.project.signup

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SmsResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName(value ="code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: SmsResult
)

data class SmsResult(
    @SerializedName("requestId")
    val requestId : String,

    @SerializedName("requestTime")
    val requestTime : String,

    @SerializedName("statusCode")
    val statusCode : String,

    @SerializedName("statusName")
    val statusName : String,

    @SerializedName("smsConfirmNum")
    val smsConfirmNum : String
)


