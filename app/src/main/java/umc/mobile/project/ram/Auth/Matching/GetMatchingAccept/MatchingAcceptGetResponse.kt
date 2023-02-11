package umc.mobile.project.ram.Auth.Matching.GetMatchingAccept

import com.google.gson.annotations.SerializedName

data class MatchingAcceptGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Int
)