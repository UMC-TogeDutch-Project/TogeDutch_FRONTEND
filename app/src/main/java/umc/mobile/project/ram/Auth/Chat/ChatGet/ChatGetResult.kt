package umc.mobile.project.ram.Auth.Chat.ChatGet

import umc.mobile.project.ram.chat.Chat

interface ChatGetResult {
    fun getChatSuccess(code: Int, result: Chat)
    fun getChatFailure(code : Int, message : String)
}