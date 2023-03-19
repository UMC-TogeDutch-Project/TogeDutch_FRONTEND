package umc.mobile.project.ram.Auth.Declaration.DeclarationPost

interface PostDeclarationResult {
    fun sendDeclarationSuccess(result: Result) // 성공했을 때, Result는 ~Response 정의한 data class
    fun sendDeclarationFailure() // 실패했을 때
}