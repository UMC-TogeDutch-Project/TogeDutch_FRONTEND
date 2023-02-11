package umc.mobile.project.ram.Auth.Application.Join

import umc.mobile.project.ram.Auth.Application.ViewUpload.ApplicationGet

interface JoinApplicationGetResult {
    fun getJoinApplicationSuccess(code: Int, result: ArrayList<ApplicationGet>)
    fun getJoinApplicationFailure(code: Int, message : String)
}