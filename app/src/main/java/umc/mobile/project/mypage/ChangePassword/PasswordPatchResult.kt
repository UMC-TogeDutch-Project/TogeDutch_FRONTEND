package umc.mobile.project.mypage.ChangePassword

interface PasswordPatchResult {
    fun changePasswordSuccess(result: ChangePasswordResult)
    fun changePasswordFailure()
}