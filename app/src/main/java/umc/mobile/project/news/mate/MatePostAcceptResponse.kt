package umc.mobile.project.news.mate

import com.google.gson.annotations.SerializedName

data class MatePostAcceptResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: ArrayList<MateBtnData>
)
