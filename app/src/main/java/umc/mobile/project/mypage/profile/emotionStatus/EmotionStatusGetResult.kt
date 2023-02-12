package umc.mobile.project.mypage.profile.emotionStatus

interface EmotionStatusGetResult {
    fun getEmotionStatusSuccess(code: Int, result: ArrayList<EmotionStatusGet>)
    fun getEmotionStatusFailure(code: Int, message: String)
}