package umc.mobile.project.signup.Auth

import com.google.gson.annotations.SerializedName

data class KeywordRequest(
    @SerializedName("word1") val word1: String,
    @SerializedName("word2") val word2: String,
    @SerializedName("word3") val word3: String,
    @SerializedName("word4") val word4: String,
    @SerializedName("word5") val word5: String,
    @SerializedName("word6") val word6: String
)
