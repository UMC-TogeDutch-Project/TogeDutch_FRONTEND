package umc.mobile.project.mypage.ChangePhoneNumber

interface PhoneNumberPatchResult {
    fun changePhoneNumberSuccess(result: ChangePhoneNumberResult)
    fun changePhoneNumberFailure()
}