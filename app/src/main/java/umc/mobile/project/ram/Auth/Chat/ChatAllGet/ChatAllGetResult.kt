package umc.mobile.project.ram.Auth.Chat.ChatAllGet

import umc.mobile.project.ram.chat.Chat

interface ChatAllGetResult {
    fun getChatAllSuccess(code: Int, result: ArrayList<Chat>)
    fun getChatAllFailure(code : Int, message : String)
}