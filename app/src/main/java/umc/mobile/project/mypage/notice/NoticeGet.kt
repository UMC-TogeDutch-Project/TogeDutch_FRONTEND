package umc.mobile.project.mypage.notice

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class NoticeGet (
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