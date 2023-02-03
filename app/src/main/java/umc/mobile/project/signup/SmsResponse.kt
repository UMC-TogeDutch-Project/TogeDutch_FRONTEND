package umc.mobile.project.signup

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SmsResponse(
    @SerializedName("requestId")
    val requestId : String,

    @SerializedName("requestTime")
    val requestTime : LocalDateTime,

    @SerializedName("statusCode")
    val statusCode : String,

    @SerializedName("statusName")
    val statusName : String,

    @SerializedName("smsConfirmNum")
    val smsConfirmNum : String
)


//data class SmsResponse(
//    @SerializedName("isSuccess")
//    val isSuccess: Boolean,
//
//    @SerializedName("code")
//    val code: Int,
//
//    @SerializedName("message")
//    val message: String,
//
//    @SerializedName("result")
//    val result: umc.mobile.project.signup.Auth.ResultSms
//)

//data class ResultSms(
//    @SerializedName("requestId")
//    val requestId : String?,
//
//    @SerializedName("requestTime")
//    val requestTime : LocalDateTime?,
//
//    @SerializedName("statusCode")
//    val statusCode : String?,
//
//    @SerializedName("statusName")
//    val statusName : String?,
//
//    @SerializedName("smsConfirmNum")
//    val smsConfirmNum : String?
//)
