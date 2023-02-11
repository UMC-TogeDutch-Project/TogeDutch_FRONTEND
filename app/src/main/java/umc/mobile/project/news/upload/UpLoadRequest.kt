package umc.mobile.project.news.upload

import com.google.gson.annotations.SerializedName

data class UpLoadRequest(
    @SerializedName("category1")
    val category1 : String?,

    @SerializedName("category2")
    val category2 : String?,

    @SerializedName("category3")
    val category3 : String?,

    @SerializedName("category4")
    val category4 : String?,

    @SerializedName("category5")
    val category5 : String?,

    @SerializedName("category6")
    val category6 : String?,

    @SerializedName("latitude")
    val latitude : Double,

    @SerializedName("longitude")
    val longitude : Double
)
