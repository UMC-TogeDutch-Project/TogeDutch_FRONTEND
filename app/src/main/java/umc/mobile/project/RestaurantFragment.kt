package umc.mobile.project
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONException
import org.json.JSONObject
//import umc.mobile.project.chat.ChatRoom
//import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.restaurant.Auth.NaverApi.NaverImageSearchAPI
import umc.mobile.project.restaurant.Auth.NaverApi.NaverPlaceSearchAPI
import umc.mobile.project.restaurant.Auth.NaverApi.NaverSearchData
import umc.mobile.project.restaurant.RestaurantData
import umc.mobile.project.restaurant.RestaurantRVAdapter
import java.io.BufferedWriter
import java.io.OutputStreamWriter


class RestaurantFragment : Fragment() {

    val clientId = "wmV0kYp4ek0ba6kCCbxB"
    val clientSecret = "PgxJ1saGO6"

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
    var mRestaurnatData = ArrayList<NaverSearchData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)

        val thread = Thread {
            var naverPlaceSearch = NaverPlaceSearchAPI()
            naverPlaceSearch.main()

        }.start()
        initRecyclerView()

        return binding.root
    }



    private fun initRecyclerView() {
        mRestaurnatData.addAll(mRestaurnatData)
        restaurantRVAdapter = RestaurantRVAdapter(mRestaurnatData)
        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
        binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결
//        mRestaurnatData.apply {
//            add(RestaurantData("파이프그라운드 ", "서울 용산구 한남대로27길 66 지하1층", "02-4948-3929", "4.1", R.drawable.img1))
//            add(RestaurantData("꽁티드툴레아 ", "서울 강남구 도산대로49길 39", "02-4938-2939", "3.3", R.drawable.img2))
//            add(RestaurantData("세상의모든아침 여의도점 ", "서울 영등포구 여의대로 24 전경련회관 50층, 51층", "02-4756-3872", "4.3", R.drawable.img3))
//            add(RestaurantData("땀땀 ", "서울 강남구 강남대로98길 12-5", "02-7663-8883", "3.5",R.drawable.img4))
//            add(RestaurantData("애플하우스 ", "서울 동작구 동작대로27다길 29 2층", "02-8839-9288", "4.0",R.drawable.img5))
//            add(RestaurantData("까폼 ", "서울 강남구 선릉로153길 18 지하1층", "02-3229-1182", "4.1",R.drawable.img6))
//            add(RestaurantData("Summer Lane ", "서울 용산구 이태원로55가길 49 1층 summerlane", "02-8837-2211", "3.3",R.drawable.img7))
//
//        }
//        Toast.makeText(context, "구글 주변장소 성공.", Toast.LENGTH_SHORT).show()



//        restaurantRVAdapter.setItemClickListener(object : RestaurantRVAdapter.OnItemClickListener {
//            override fun onItemClick(restaurantData: Result) {
//                val dlg = context?.let { RestaurantPageDialog(it) }
//                if (dlg != null) {
//                    dlg.start()
//                }
////                Intent(context, RestaurantPageActivity::class.java).apply {
////                    putExtra("data", mRestaurnatData)
////                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                }.run { context?.startActivity(this) }
//            }
//        })

    }
//    private fun getNaverSearch(){
//        val naverSearchGetService = NaverSearchGetService()
//        naverSearchGetService.setNaverGetResult(this)
//        naverSearchGetService.getPost("1FCcaRaIxyqo6u88ujMz", "wYSHyMWPW2", "local.json","가능동")
//
//    }



//    override fun getPostSuccess(result: ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>) {
//        mRestaurnatData.addAll(result)
//        restaurantRVAdapter = RestaurantRVAdapter(mRestaurnatData)
//        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
//        binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결
//        Toast.makeText(context, "구글 주변장소 성공.", Toast.LENGTH_SHORT).show()
//
//
//
////        restaurantRVAdapter.setItemClickListener(object : RestaurantRVAdapter.OnItemClickListener {
////            override fun onItemClick(restaurantData: Place) {
////                val dlg = context?.let { RestaurantPageDialog(it) }
////                if (dlg != null) {
////                    dlg.start()
////                }
////            }
////        })
//    }

//    override fun getPostFailure() {
//
//    }


}