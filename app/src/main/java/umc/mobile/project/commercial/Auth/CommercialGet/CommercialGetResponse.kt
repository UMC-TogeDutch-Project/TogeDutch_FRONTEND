package umc.mobile.project.commercial.Auth.CommercialGet

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class CommercialGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : ArrayList<CommercialGet>
)

