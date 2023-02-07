package umc.mobile.project.news

import com.google.gson.annotations.SerializedName

data class UpLoadRequest(
    @SerializedName("category")
    val category : String,

    @SerializedName("latitude")
    val latitude : Double,

    @SerializedName("longitude")
    val longitude : Double
)
