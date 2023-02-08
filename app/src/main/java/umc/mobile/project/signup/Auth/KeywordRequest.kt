package umc.mobile.project.signup.Auth

import com.google.gson.annotations.SerializedName

data class KeywordRequest(
    @SerializedName("word1") val word1: String? = null,
    @SerializedName("word2") val word2: String? = null,
    @SerializedName("word3") val word3: String? = null,
    @SerializedName("word4") val word4: String? = null,
    @SerializedName("word5") val word5: String? = null,
    @SerializedName("word6") val word6: String? = null
)
