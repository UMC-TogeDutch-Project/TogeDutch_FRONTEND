package umc.mobile.project.commercial.Auth.CommercialDetailGet

import com.google.gson.annotations.SerializedName
import umc.mobile.project.commercial.Auth.CommercialGet.CommercialGet


data class CommercialDetailGetResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : CommercialGet
)
