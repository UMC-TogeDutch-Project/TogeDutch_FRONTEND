package umc.mobile.project.search

import Post

interface SearchPostResult {
    fun searchSuccess(result: ArrayList<Post>)
    fun searchFailure()
}