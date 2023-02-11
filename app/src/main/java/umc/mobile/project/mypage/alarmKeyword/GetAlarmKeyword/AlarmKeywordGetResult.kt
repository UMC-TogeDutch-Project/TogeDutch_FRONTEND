package umc.mobile.project.mypage.alarmKeyword.GetAlarmKeyword


interface AlarmKeywordGetResult {
    fun getKeywordSuccess(code: Int, result: KeywordData)
    fun getKeywordFailure(code : Int, message : String)
}