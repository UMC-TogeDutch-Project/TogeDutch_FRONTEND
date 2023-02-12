package umc.mobile.project.mypage.notice.all

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class GetAllNoticeResponse (
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: ArrayList<Notice>
)

data class Notice (
    @SerializedName("notice_id")
    var noticeId: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("content")
    var content: String,

    @SerializedName("created_at")
    var created_at: Timestamp,

    @SerializedName("updated_at")
    var updated_at: Timestamp
)