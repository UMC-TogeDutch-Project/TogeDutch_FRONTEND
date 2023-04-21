package umc.mobile.project.restaurant.blog

import NaverBlogAPI
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import umc.mobile.project.RestaurantFragment
import umc.mobile.project.databinding.ActivityRestaurantPageBinding
import umc.mobile.project.mSearchResult
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

    var searchResult: String? = null
    var defaultResult = ""
    var useResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.setOnClickListener{
            finish()
        }


        binding.resTitle.text = intent.getStringExtra("data1")
        val titleData = intent.getStringExtra("data1")

        binding.resAddress.text = intent.getStringExtra("data2")
        val addressData = intent.getStringExtra("data2")

        binding.resMenu.text = intent.getStringExtra("data3")

        Glide.with(this).load(intent.getStringExtra("data4")).into(binding.resImg)



        binding.resAddress.setOnClickListener {
            Intent(this, RestaurantPlaceActivity::class.java).apply {
                putExtra("addressData", addressData)
                putExtra("titleData", titleData)

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { startActivity(this) }
        }


        val thread = Thread{
            var dAddress = RestaurantFragment()

            defaultResult = dAddress.defaultAddress(this)

            if (mSearchResult != null){
                useResult = mSearchResult as String
            }else{
                useResult = defaultResult
            }



            var naverPlaceSearch = RestaurantFragment()
            resultT =  naverPlaceSearch.main2(useResult)
            var naverblog = NaverBlogAPI()

            result = naverblog.main(resultT[intent.getIntExtra("position", 0)])
            runOnUiThread{
                blogRVAdapter = BlogRVAdapter(result)
                binding.rvBlog.adapter = blogRVAdapter //리사이클러뷰에 어댑터 연결
                binding.rvBlog.layoutManager = LinearLayoutManager(this) //레이아웃 매니저 연결

            }
        }.start()

    }



}