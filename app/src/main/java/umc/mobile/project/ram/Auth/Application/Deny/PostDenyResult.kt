package umc.mobile.project.ram.Auth.Application.Deny

interface PostDenyResult {
    fun DenySuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun DenyFailure() // 실패했을 때
}