package umc.mobile.project.search

interface SearchPostResult {
    fun searchSuccess(result: Result)
    fun searchFailure()
}