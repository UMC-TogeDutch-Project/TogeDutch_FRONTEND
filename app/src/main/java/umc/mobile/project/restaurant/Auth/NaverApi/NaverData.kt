package umc.mobile.project.restaurant.Auth.NaverApi

import com.google.gson.annotations.SerializedName

sealed class NaverData{
    data class NaverSearchData(
        @SerializedName(value =  "title") val title: String,
        @SerializedName(value =  "category")val category: String,
        @SerializedName(value =  "description")val description: String,
        @SerializedName(value =  "address")val address: String
    ): NaverData()
    data class NaverImgData(
        @SerializedName(value =  "thumbnail")val thumbnail: String
    ):NaverData()
    data class NaverTitle(
        @SerializedName(value =  "title")val title: String
    ):NaverData()

}



