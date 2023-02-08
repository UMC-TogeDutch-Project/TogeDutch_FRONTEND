package umc.mobile.project.ram.Auth.Matching.GetMatching

import umc.mobile.project.MemberData

interface MatchingGetResult {
    fun getMatchingSuccess(code: Int, result: MemberData)
    fun getMatchingFailure(code : Int, message : String)
}