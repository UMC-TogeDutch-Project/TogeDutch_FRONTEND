package umc.mobile.project.withdrawal;

public interface WithdrawalResult {
    fun changeUserStatusSuccess(result: ChangeUserStatusResult)
    fun changeUserStatusFailure()
}