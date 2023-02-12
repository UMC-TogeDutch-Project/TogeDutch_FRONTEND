package umc.mobile.project.mypage.notice.detail

import com.google.gson.annotations.SerializedName
import umc.mobile.project.mypage.notice.NoticeGet
import java.sql.Timestamp

data class GetNoticeResponse (
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: NoticeGet
)