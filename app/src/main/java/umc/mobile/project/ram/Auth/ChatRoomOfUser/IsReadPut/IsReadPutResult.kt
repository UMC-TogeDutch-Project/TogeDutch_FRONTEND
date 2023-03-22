package umc.mobile.project.ram.Auth.ChatRoomOfUser.IsReadPut

interface IsReadPutResult {
    fun putIsReadSuccess(code: Int, result: Result)
    fun putIsReadFailure(code : Int, message : String)
}