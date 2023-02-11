package umc.mobile.project.news.mate

import com.google.gson.annotations.SerializedName

data class MateData(
    @SerializedName("application_id")
    val application_id : Int,

    @SerializedName("status")
    val status : String,

    @SerializedName("post_id")
    val post_id : Int,

    @SerializedName("user_id")
    val user_id : Int,

    @SerializedName("chatRoom_id")
    val chatRoom_id : Int

)
