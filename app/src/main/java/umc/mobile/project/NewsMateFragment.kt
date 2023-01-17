package umc.mobile.project


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import umc.mobile.project.databinding.FragmentNewsMateBinding
import umc.mobile.project.databinding.FragmentNewsUploadBinding


class NewsMateFragment : Fragment(){

    private var _viewBinding: FragmentNewsMateBinding? = null
    private val viewBinding get() = _viewBinding!!


    private var dummyMateData = ArrayList<MateData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _viewBinding = FragmentNewsMateBinding.inflate(inflater, container, false)

        dummyMateData.apply {
            add(MateData("2023년 01월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2027년 02월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 03월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 04월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 05월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 06월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 07월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
            add(MateData("2023년 08월 16일 오후 10시 27분", R.drawable.main_rv_item_image,"버거킹 같이 시키실 분 구합니다!", "* * *님이 메이트를 신청했습니다."))
        }
        //더미데이터와 리사이클러뷰 연결
        val mateDataRVAdapter = MateDataRVAdapter(dummyMateData)

        //리사이클러뷰를 어댑터에 연결
        viewBinding.rvMain.adapter = mateDataRVAdapter

        viewBinding.rvMain.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}