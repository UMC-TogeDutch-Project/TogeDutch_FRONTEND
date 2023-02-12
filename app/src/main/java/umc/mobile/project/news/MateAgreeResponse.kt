package umc.mobile.project.news

import com.google.gson.annotations.SerializedName
import umc.mobile.project.news.mate.MateData


data class MateAgreeResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: ArrayList<MateData>
)
