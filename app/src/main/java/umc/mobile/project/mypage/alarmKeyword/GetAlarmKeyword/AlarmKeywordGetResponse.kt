package umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword

import com.google.gson.annotations.SerializedName

data class AlarmKeywordGetResponse (
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : KeywordData
)