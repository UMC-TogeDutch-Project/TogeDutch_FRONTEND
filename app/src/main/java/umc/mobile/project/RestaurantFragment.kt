package umc.mobile.project
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
//import umc.mobile.project.chat.ChatRoom
//import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGetResult
import umc.mobile.project.restaurant.Auth.PlaceApi.PlaceGetService
import umc.mobile.project.restaurant.RestaurantRVAdapter

class RestaurantFragment : Fragment(), PlaceGetResult {
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
    var mRestaurnatData = ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>()
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
        getPlace()
//        mRestaurnatData.apply {
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ", "동덕여대 인문관 앞", "3시 30분 주문", "2/3"))
//
//        }

    }
private fun getPlace(){
    val currentLocation = LatLng(latitude_var, longtitude_var)
    val placeGetService = PlaceGetService()
    placeGetService.setPlaceGetResult(this)
    placeGetService.getPost( 1000,  "restaurant","ko", "AIzaSyCC4KrkEvk1cYehJPuA1hliUc8hPqxE-aQ")
}



    override fun getPostSuccess(result: ArrayList<umc.mobile.project.restaurant.Auth.PlaceApi.Place>) {
        mRestaurnatData.addAll(result)
        restaurantRVAdapter = RestaurantRVAdapter(mRestaurnatData)
        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
        binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결
        Toast.makeText(context, "구글 주변장소 성공.", Toast.LENGTH_SHORT).show()



//        restaurantRVAdapter.setItemClickListener(object : RestaurantRVAdapter.OnItemClickListener {
//            override fun onItemClick(restaurantData: Place) {
//                val dlg = context?.let { RestaurantPageDialog(it) }
//                if (dlg != null) {
//                    dlg.start()
//                }
//            }
//        })
    }

    override fun getPostFailure() {

    }


}