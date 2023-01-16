package umc.mobile.project


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.FragmentNewsUploadBinding


class NewsUpLoadFragment : Fragment(){

    private var _viewBinding: FragmentNewsUploadBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var dummyUpLoadData = ArrayList<UpLoadData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentNewsUploadBinding.inflate(inflater, container, false)

        dummyUpLoadData.apply {
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
            add(UpLoadData("버거킹 같이 시키실 분 구합니다!", R.drawable.main_rv_item_image, "aeeazip", "덕성여대 인문관 앞", "3시 30분 주문"))
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