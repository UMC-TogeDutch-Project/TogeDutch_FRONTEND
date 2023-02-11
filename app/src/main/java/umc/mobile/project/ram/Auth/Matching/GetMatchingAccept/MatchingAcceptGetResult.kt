package umc.mobile.project.ram.Auth.Matching.GetMatchingAccept

interface MatchingAcceptGetResult {
    fun getMatchingAcceptSuccess(code: Int, result: Int)
    fun getMatchingAcceptFailure(code: Int, message : String)
}