package umc.mobile.project.commercial.Auth.CommercialGet

interface CommercialGetResult {
    fun commercialGetSuccess(result: ArrayList<CommercialGet>) // 성공했을 때, Result는 ~Response 정의한 data class
    fun commercialGetFailure() // 실패했을 때
}