package umc.mobile.project.ram.Auth.Matching.GetMatching

import MemberData

interface MatchingGetResult {
    fun getMatchingSuccess(code: Int, result: MemberData)
    fun getMatchingFailure(code : Int, message : String)
}