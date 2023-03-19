package umc.mobile.project.ram.Auth.Chat.ChatMember

import umc.mobile.project.ram.chat.Chat

interface ChatMemberGetResult {
    fun getChatMemberSuccess(code: Int, result: ArrayList<ChatMember>)
    fun getChatMemberFailure(code : Int, message : String)
}