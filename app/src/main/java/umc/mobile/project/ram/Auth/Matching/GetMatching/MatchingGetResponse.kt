package umc.mobile.project.ram.Auth.Matching.GetMatching

import com.google.gson.annotations.SerializedName
import umc.mobile.project.MemberData

data class MatchingGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : MemberData
)