package umc.mobile.project.ram.Auth.Chat.ChatDelete

interface UserDeleteResult {
    fun userDeleteSuccess(code: Int, result: Int)
    fun userDeleteFailure(code : Int, message : String)
}