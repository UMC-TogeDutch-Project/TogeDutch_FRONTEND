package umc.mobile.project.wishlist

import Post
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.ActivityWishlistBinding
import umc.mobile.project.ram.my_application_1.MyCommercialDetailActivity
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.wishlist.GetLikePost.LikePostGetResult
import umc.mobile.project.wishlist.GetLikePost.LikePostGetService

class WishListActivity: AppCompatActivity(), LikePostGetResult {
    lateinit var viewBinding: ActivityWishlistBinding
    //lateinit var wishListRVAdapter: WishListRVAdapter
    val wishApplicationList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //viewBinding.searchMyWish.setOnQueryTextListener(searchViewTextListener)
    }

    override fun onResume() {
        super.onResume()
        initActionBar()
        getLikePost()
    }

    private fun initActionBar() {

        viewBinding.include.appbarPageNameLeftTv.text = "관심 목록"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    private fun initRecyclerView(result : ArrayList<Post>) {
       val wishListRVAdapter = WishListRVAdapter(result)
        viewBinding.wishList.adapter = wishListRVAdapter
        viewBinding.wishList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        wishListRVAdapter.setItemClickListener(object : WishListRVAdapter.OnItemClickListener {
            override fun onItemClick(post: Post) {
                val intent = Intent(this@WishListActivity, MyCommercialDetailActivity::class.java)
                intent.putExtra("post_id", post.post_id)
                startActivity(intent)
            }
        })
    }

    private fun getLikePost() {
        val likePostGetService = LikePostGetService()
        likePostGetService.setLikePostGetResult(this)
        likePostGetService.getLikePost(user_id_logined)
        user_id_var = user_id_logined // 상세목록 볼 때 현재 로그인된 유저를 보여줄 수 있게 덮어씌워주기
    }

    override fun getPostUploadSuccess(code: Int, result: ArrayList<Post>) {
       initRecyclerView(result)
        Toast.makeText(this, "관심목록 불러오기 성공", Toast.LENGTH_SHORT).show()
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Toast.makeText(this, "관심목록 불러오기 실패", Toast.LENGTH_SHORT).show()
    }

    // 검색 기능
//    var searchViewTextListener: SearchView.OnQueryTextListener =
//        object : SearchView.OnQueryTextListener {
//            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
//            override fun onQueryTextSubmit(s: String): Boolean {
//                return false
//            }
//
//            //텍스트 입력/수정시에 호출
//            override fun onQueryTextChange(s: String): Boolean {
//                WishListRVAdapter.getFilter().filter(s)
//                Log.d(TAG, "SearchVies Text is changed : $s")
//                return false
//            }
//        }
}
