package umc.mobile.project.ram.Auth.ChatLocation

interface PostLocationResult {
    fun sendLocationSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun sendLocationFailure() // 실패했을 때
}