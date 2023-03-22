package umc.mobile.project.ram.Auth.ChatRoomOfUser.IsOutPut

interface IsOutPutResult {
    fun putIsOutSuccess(code: Int, result: Result)
    fun putIsOutFailure(code : Int, message : String)
}