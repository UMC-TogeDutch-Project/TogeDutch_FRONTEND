package umc.mobile.project.wishlist

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityWishlistBinding
import umc.mobile.project.ram.my_application_1.JoinRVAdatpter
import umc.mobile.project.ram.my_application_1.Post
import umc.mobile.project.ram.my_application_1.ReviewWritePopupDialog

class WishListActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityWishlistBinding
    lateinit var wishListRVAdapter: WishListRVAdapter
    val wishApplicationList = ArrayList<WishApplication>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initActionBar()
        initRecyclerView()

        //viewBinding.searchMyWish.setOnQueryTextListener(searchViewTextListener)
    }

    private fun initActionBar() {

        viewBinding.include.appbarPageNameLeftTv.text = "관심 목록"

        viewBinding.include.appbarBackBtn.setOnClickListener {
            finish()
        }

    }

    private fun initRecyclerView() {
        wishApplicationList.apply {
            add(WishApplication(7, "오매떡 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 4000, 20000, "2023-01-15T03:04:56.000+00:00",
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 0.0, 0.0, 5, "떡볶이", false
                )
            )
            add(
                WishApplication(8, "엽떡 2인으로 같이 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 1000, 130000, "2023-01-15T03:04:56.000+00:00",
                    2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 0.0, 0.0, 4, "떡볶이", false
                )
            )
        }

        wishListRVAdapter = WishListRVAdapter(wishApplicationList)
        viewBinding.orderList.adapter = wishListRVAdapter

//        wishListRVAdapter.setItemClickListener(object:
//            WishListRVAdapter.OnItemClickListener {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onItemClick(wishApplication: WishApplication) {
//            }
//        })


        wishListRVAdapter.notifyDataSetChanged()
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
