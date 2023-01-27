package umc.mobile.project.wishlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mobile.project.databinding.ActivityWishlistBinding

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
                2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 0.0, 0.0, 5, "떡볶이"
                )
            )
            add(
                WishApplication(8, "엽떡 2인으로 같이 시킬 사람 구해요", "https://baemin.me/1A5x-ZYDB", 1000, 130000, "2023-01-15T03:04:56.000+00:00",
                    2, 1, "모집중", "2023-01-15T01:43:39.000+00:00", null, 0.0, 0.0, 4, "떡볶이"
                )
            )
        }

        wishListRVAdapter = WishListRVAdapter(wishApplicationList)
        viewBinding.orderList.adapter = wishListRVAdapter


        wishListRVAdapter.notifyDataSetChanged()
    }
}