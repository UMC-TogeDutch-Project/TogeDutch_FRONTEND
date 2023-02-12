package umc.mobile.project.mypage.profile

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ReviewGet (
    @SerializedName("reviewId")
    var reviewId: Int,

    @SerializedName("emotionStatus")
    var emotionStatus: Int,

    @SerializedName("content")
    var content: String,

    @SerializedName("created_at")
    var created_at: Timestamp,

    @SerializedName("applicationId")
    var applicationId: Int,

    @SerializedName("postId")
    var postId: Int,

    @SerializedName("userId")
    var userId: Int,
)