package umc.mobile.project.commercial.Auth.RefundPost

interface RefundResult {
    fun refundSuccess(
        amount: Amount,
        approvedCancelAmount: Approved_cancel_amount,
        canceledAmount: Canceled_amount,
        cancelAvailableAmount: Cancel_available_amount
    ) // 성공했을 때, Result는 ~Response 정의한 data class
    fun refundFailure(code : Int, msg : String) // 실패했을 때
}