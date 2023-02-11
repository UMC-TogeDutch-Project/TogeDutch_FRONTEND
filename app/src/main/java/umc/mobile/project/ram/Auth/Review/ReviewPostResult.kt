package umc.mobile.project.ram.Auth.Review

interface ReviewPostResult {
    fun reviewSuccess(result: Integer)
    fun reviewFailure()
}