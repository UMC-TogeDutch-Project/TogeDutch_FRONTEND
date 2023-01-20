package umc.mobile.project.restaurant.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.announcement.AnnounceRVAdapterDecoration
import umc.mobile.project.databinding.ActivityCommercialListBinding

class RestaurantPageActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCommercialListBinding
    private lateinit var blogRVAdapter: BlogRVAdapter
    var mBlogData = ArrayList<BlogData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommercialListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView(){
        mBlogData.apply{
            add(BlogData("튀김이 맛있는 떡볶이 동덕여대 오매떡","2022.06.15.13:49"))

        }
        blogRVAdapter = BlogRVAdapter(mBlogData)
        binding.recyclerView.adapter=blogRVAdapter //리사이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결
        binding.recyclerView.addItemDecoration(AnnounceRVAdapterDecoration(20))
    }
}