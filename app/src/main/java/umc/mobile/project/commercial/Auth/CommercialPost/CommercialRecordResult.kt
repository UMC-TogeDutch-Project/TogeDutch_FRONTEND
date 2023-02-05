package umc.mobile.project.commercial.Auth.CommercialPost

interface CommercialRecordResult {
    fun commercialRecordSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun commercialRecordFailure() // 실패했을 때
}