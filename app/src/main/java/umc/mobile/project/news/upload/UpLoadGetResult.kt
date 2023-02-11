package umc.mobile.project.news.upload

interface UpLoadGetResult {

    fun recordSuccess(result: ArrayList<UpLoadData>) // 성공했을 때, Result는 ~Response 정의한 data class
    fun recordFailure() // 실패했을 때

}