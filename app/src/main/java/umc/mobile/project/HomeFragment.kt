package umc.mobile.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    lateinit var viewBinding: FragmentHomeBinding

    private var dummyDatas = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        //더미데이터
        dummyDatas.apply {
            add(Data("최신순", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지"))
            add(Data("마감임박", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지", R.drawable.main_rv_item_image, "버거킹 같이 시키실분!", "덕성여대 인문관 앞", "4시 30분까지"))
        }

        //더미데이터와 리사이클러뷰 연결
        val dataRVAdapter = DataRVAdapter(dummyDatas)

        //리사이클러뷰를 어댑터에 연결
        viewBinding.rvMain.adapter = dataRVAdapter

        viewBinding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewBinding.root


    }
}