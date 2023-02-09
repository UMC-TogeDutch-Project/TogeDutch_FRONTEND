package umc.mobile.project.mypage.withdrawal;

public interface WithdrawalResult {
    fun changeUserStatusSuccess(result: ChangeUserStatusResult)
    fun changeUserStatusFailure()
}