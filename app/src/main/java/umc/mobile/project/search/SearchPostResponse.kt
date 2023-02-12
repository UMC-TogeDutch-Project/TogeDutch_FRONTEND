package umc.mobile.project.search

import Post
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class SearchPostResponse (
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: ArrayList<Post>
)


