package umc.mobile.project.mypage.GetUser

import MemberData

interface UserGetResult {
    fun getUserSuccess(code: Int, result: MemberData)
    fun getUserFailure(code : Int, message : String)
}