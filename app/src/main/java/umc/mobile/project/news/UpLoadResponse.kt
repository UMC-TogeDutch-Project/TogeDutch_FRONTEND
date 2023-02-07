package umc.mobile.project.news

import com.google.gson.annotations.SerializedName

data class UpLoadResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: Result?
) {
    data class Result(
        @SerializedName("post_id")
        val post_id: Int,

        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("delivery_tips")
        val delivery_tips: Int,

        @SerializedName("minimum")
        val minimum: Int,

        @SerializedName("order_time")
        val order_time: String,

        @SerializedName("num_of_recruits")
        val num_of_recruits: Int,

        @SerializedName("recruited_num")
        val recruited_num: Int,

        @SerializedName("status")
        val status: String,

        @SerializedName("created_at")
        val created_at: String,

        @SerializedName("updated_at")
        val updated_at: String,

        @SerializedName("latitude")
        val latitude: Double,

        @SerializedName("longitude")
        val longitude: Double,

        @SerializedName("chatRoom_id")
        val chatRoom_id: Int,

        @SerializedName("category")
        val category: String,

        @SerializedName("image")
        val image: String?,

        @SerializedName("user_id")
        val user_id: Int?

    )
}