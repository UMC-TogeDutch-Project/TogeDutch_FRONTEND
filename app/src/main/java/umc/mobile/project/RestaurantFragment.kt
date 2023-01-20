package umc.mobile.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.restaurant.RestaurantData
import umc.mobile.project.restaurant.RestaurantRVAdapter
import umc.mobile.project.restaurant.RestaurantRVAdapterDecoration

class RestaurantFragment : Fragment() {
    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
    var mRestaurnatData = ArrayList<RestaurantData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView(){
        mRestaurnatData.apply{
            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))
            add(RestaurantData("버거킹 같이 시키실 분 구합니다 ","동덕여대 인문관 앞","3시 30분 주문","2/3"))

        }
        restaurantRVAdapter = RestaurantRVAdapter(mRestaurnatData)
        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
        binding.rvRes.layoutManager= LinearLayoutManager(requireContext()) //레이아웃 매니저 연결
        binding.rvRes.addItemDecoration(RestaurantRVAdapterDecoration(20))
    }
}