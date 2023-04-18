package umc.mobile.project.restaurant.blog

import NaverBlogAPI
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import umc.mobile.project.RestaurantFragment
import umc.mobile.project.announcement.AnnounceRVAdapterDecoration
import umc.mobile.project.databinding.ActivityCommercialListBinding
import umc.mobile.project.databinding.ActivityRestaurantPageBinding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverData
import umc.mobile.project.restaurant.RestaurantData
import umc.mobile.project.restaurant.RestaurantRVAdapter

class RestaurantPageActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantPageBinding
    private lateinit var blogRVAdapter: BlogRVAdapter
    var mBlogData = ArrayList<BlogData>()
    private lateinit var datas : ArrayList<RestaurantData>

    var resultT = ArrayList<NaverData.NaverTitle>()

    var result = ArrayList<BlogData>()
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener{
            finish()
        }
//        datas = intent.getSerializableExtra("data") as ArrayList<RestaurantData>
//
//        Glide.with(this).load(datas.image).into(binding.resImg)
//        binding.resTitle.text = datas.title
//        binding.resAddress.text = datas.place
//        binding.resPhone.text = datas.phone
//        binding.resMenu.text = datas.score

        binding.resTitle.text = intent.getStringExtra("data1")
        binding.resAddress.text = intent.getStringExtra("data2")
        binding.resMenu.text = intent.getStringExtra("data3")
        Glide.with(this).load(intent.getStringExtra("data4")).into(binding.resImg)


        val thread = Thread{
            var naverPlaceSearch = RestaurantFragment()
            resultT =  naverPlaceSearch.main2()
            var naverblog = NaverBlogAPI()
            Log.d("blogT 입니당", naverblog.main(resultT[0]).toString())
            Log.d("blogT 입니당", naverblog.main(resultT[1]).toString())
            Log.d("blogT 입니당", naverblog.main(resultT[2]).toString())
            Log.d("blogT 입니당", naverblog.main(resultT[3]).toString())

            result = naverblog.main(resultT[4])
            runOnUiThread{
                blogRVAdapter = BlogRVAdapter(result)
                binding.rvBlog.adapter=blogRVAdapter //리사이클러뷰에 어댑터 연결
                binding.rvBlog.layoutManager= LinearLayoutManager(this) //레이아웃 매니저 연결

            }
        }.start()

    }


    private fun initRecyclerView(){
        mBlogData.apply{
//            add(BlogData("튀김이 맛있는 떡볶이 동덕여대 오매떡","2022.06.15.13:49"))
//            add(BlogData("튀김이 맛있는 떡볶이 동덕여대 오매떡","2022.06.15.13:49"))
//            add(BlogData("튀김이 맛있는 떡볶이 동덕여대 오매떡","2022.06.15.13:49"))

        }
    }
}