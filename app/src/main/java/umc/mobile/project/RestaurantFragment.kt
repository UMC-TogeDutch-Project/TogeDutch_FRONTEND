package umc.mobile.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
//import umc.mobile.project.chat.ChatRoom
//import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.PostRetouchActivity
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGet
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGetResponse
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGetResult
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGetService
import umc.mobile.project.restaurant.RestaurantData
import umc.mobile.project.restaurant.RestaurantPageDialog
import umc.mobile.project.restaurant.RestaurantRVAdapter

class RestaurantFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
    var mRestaurnatData = ArrayList<PlaceGet>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
//        mRestaurnatData.apply {
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//
//        }

    }
//private fun getPlace(){
//    val geocoderLocation = Geocoder_location()
//    val location = geocoderLocation.calculate_location(context as PostRetouchActivity, )
//
//    val placeGetService = PlaceGetService()
//    placeGetService.setPlaceGetResult(this)
//    placeGetService.getPost()
//}

//    override fun getPostSuccess(result: PlaceGetResponse) {
//        restaurantRVAdapter = RestaurantRVAdapter(mRestaurnatData)
//        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
//        binding.rvRes.layoutManager = LinearLayoutManager(requireContext()) //레이아웃 매니저 연결
//
//
//        restaurantRVAdapter.setItemClickListener(object : RestaurantRVAdapter.OnItemClickListener {
//            override fun onItemClick(restaurantData: RestaurantData) {
//                val dlg = context?.let { RestaurantPageDialog(it) }
//                if (dlg != null) {
//                    dlg.start()
//                }
//            }
//        })
//    }
//
//    override fun getPostFailure() {
//        TODO("Not yet implemented")
//    }


}