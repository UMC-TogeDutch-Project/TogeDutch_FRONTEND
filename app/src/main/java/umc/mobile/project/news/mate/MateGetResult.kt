package umc.mobile.project.news.mate

import umc.mobile.project.news.upload.UpLoadData

interface MateGetResult {
    fun recordSuccess(result: ArrayList<MateData>) // 성공했을 때, Result는 ~Response 정의한 data class
    fun recordFailure() // 실패했을 때
}