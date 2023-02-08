package umc.mobile.project.mypage.GetUser

import umc.mobile.project.MemberData

interface UserGetResult {
    fun getUserSuccess(code: Int, result: MemberData)
    fun getUserFailure(code : Int, message : String)
}