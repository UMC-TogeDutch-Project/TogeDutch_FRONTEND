package umc.mobile.project.mypage.alarmKeyword.PatchAlarmKeyword

interface AlarmKeywordPatchResult {
    fun changeKeywordSuccess(result: ChangeKeywordResult)
    fun changeKeywordFailure()
}