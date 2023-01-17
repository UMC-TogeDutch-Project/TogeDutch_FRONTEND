package umc.mobile.project.news


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.R
import umc.mobile.project.databinding.FragmentNewsUploadBinding


class NewsUpLoadFragment : Fragment(){

    private var _viewBinding: FragmentNewsUploadBinding? = null
    private val viewBinding get() = _viewBinding!!

    //더미데이터 리스트
    private var dummyUpLoadData = ArrayList<UpLoadData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentNewsUploadBinding.inflate(inflater, container, false)


        //더미데이터
        dummyUpLoadData.apply {
            add(UpLoadData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 02월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 03월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 04월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 05월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 06월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 07월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 08월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 09월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))


        }

        //더미데이터와 리사이클러뷰 연결
        val upLoadDataRVAdapter = UpLoadDataRVAdapter(dummyUpLoadData)

        //리사이클러뷰를 어댑터에 연결
        viewBinding.rvMain.adapter = upLoadDataRVAdapter

        viewBinding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}