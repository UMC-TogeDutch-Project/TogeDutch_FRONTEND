package umc.mobile.project.restaurant.Auth.NaverApi

import com.google.gson.annotations.SerializedName

data class NaverSearchData(
    @SerializedName(value =  "title") val title : String,
    @SerializedName(value =  "category") val category : String,
    @SerializedName(value =  "description") val description : String,
    @SerializedName(value =  "address") val address : String,


    )
