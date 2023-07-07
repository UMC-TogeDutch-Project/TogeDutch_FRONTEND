package umc.mobile.project.mypage.profile.profileImage

import umc.mobile.project.MemberData


interface profileImagePatchResult {
    fun profileImageSuccess(code: Int, result: ChangeProfileResult)
    fun profileImageFailure(code : Int, message : String)
}