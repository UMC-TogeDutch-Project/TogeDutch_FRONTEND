package umc.mobile.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import umc.mobile.project.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val TAG: String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://ec2-3-34-255-129.ap-northeast-2.compute.amazonaws.com:9000/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(AdsRandomApiService::class.java)
//
//        apiService.adsRandom()?.enqueue(object : Callback<AdsRandomResponse> {
//            override fun onResponse(
//                call: Call<AdsRandomResponse>,
//                response: Response<AdsRandomResponse>
//            ) {
//                Log.d(TAG, "onResponse:adsRandom요청 성공")
//                if(response.isSuccessful) {
//                    val adsRandomResponseData = response.body()
//                    Log.d(TAG, "onResponse:adsRandom요청값 정상 ${adsRandomResponseData}")
//                    if(adsRandomResponseData != null){
//                        Log.d(TAG, "onResponse:..nullCheck")
//
//                        when(adsRandomResponseData.code){
//                            1000 -> {
////                                viewBinding.
////                                Log.d(TAG, "onResponse:1000번 진입")
//                                var homeFragment = HomeFragment()
//                                val adsTitle : String = adsRandomResponseData.result!!.store
//                                val adsSubmessage: String = adsRandomResponseData.result.request
//                                val adsImage: String = adsRandomResponseData.result.image
//                                val bundle: Bundle = Bundle()
//                                Log.d(TAG, "onResponse:${adsTitle}, ${adsSubmessage}, ${adsImage}")
//                                bundle.putString("${adsTitle}", adsTitle)
//                                bundle.putString("${adsSubmessage}", adsSubmessage)
//                                bundle.putString("${adsImage}", adsImage)
//                                homeFragment.arguments = bundle
//                            }
//                        }
//                    }
//                    else{
//
//                    }
//                }
//                else{
//
//                }
//            }
//
//            override fun onFailure(call: Call<AdsRandomResponse>, t: Throwable) {
//                Log.d(TAG, "onResponse: 요청 실패")
//            }
//
//        })


        setContentView(viewBinding.root)

        // 해시 키 구하기
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.containerFragment.id, HomeFragment())
            .commitAllowingStateLoss()

        viewBinding.navBottom.run {
            setOnItemSelectedListener {
                when (it.itemId){
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, HomeFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_chat -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, ChatFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_restaurant -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, RestaurantFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_mypage -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, MyPageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            //처음 실행시 자동으로 home 화면을 가르키게 됨.
            selectedItemId = R.id.menu_home
        }
        // 공고 등록버튼 클릭시 post 화면으로 이동
        viewBinding.addBtn.setOnClickListener{
            val intent = Intent(this, AnnouncePostActivity::class.java)
            startActivity(intent)
        }
    }
}