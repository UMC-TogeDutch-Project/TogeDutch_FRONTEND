package umc.mobile.project.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantData(
    @SerializedName("title")val title: String,
    @SerializedName("place")val place: String,
    @SerializedName("phone")val phone: String,
    @SerializedName("score")val score: String,
    @SerializedName("image")val image: Int
        )

