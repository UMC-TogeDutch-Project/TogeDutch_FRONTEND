package umc.mobile.project.commercial.Auth.CommercialPost

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

data class CommercialRecord(
    @SerializedName(value =  "store") val store : String,
    @SerializedName(value =  "information") val information : String,
    @SerializedName(value =  "mainMenu") val mainMenu : String,
    @SerializedName(value =  "deliveryTips") val deliveryTips : Int,
    @SerializedName(value =  "latitude") val latitude : Double,
    @SerializedName(value =  "longitude") val longitude : Double,
    @SerializedName(value =  "request") val request : String,
    @SerializedName(value =  "userIdx") val userIdx : Int,

)
