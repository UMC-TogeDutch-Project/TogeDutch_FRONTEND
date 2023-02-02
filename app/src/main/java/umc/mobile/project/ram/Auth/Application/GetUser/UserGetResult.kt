package umc.mobile.project.ram.Auth.Application.GetUser

interface UserGetResult {
    fun getUserSuccess(code: Int, result: UserGet)
    fun getUserFailure(code : Int, message : String)
}