package umc.mobile.project.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import umc.mobile.project.databinding.FragmentMyprofileBinding


class MyProfileFragment : Fragment() {
    private lateinit var viewBinding: FragmentMyprofileBinding
    private lateinit var orderRVAdapter: OrderRVAdapter
    var orderList = ArrayList<OrderData>()
    var reviewList = ArrayList<ReviewData>()

    var myProfileActivity:MyProfileActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myProfileActivity = context as MyProfileActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMyprofileBinding.inflate(layoutInflater)

        initRecyclerView()
        initReview()

        viewBinding.profileRevise.setOnClickListener {
            myProfileActivity!!.replaceFragment(1)
        }

        viewBinding.phoneNumRevise.setOnClickListener {
            myProfileActivity!!.replaceFragment(2)
        }

        // 뒤로 가기
        viewBinding.myprofileActionbar.appbarBackBtn.setOnClickListener {
            (context as MyProfileActivity).finish()
        }

        return viewBinding.root
    }

    private fun initRecyclerView() {
        orderList.apply{
            add(OrderData("푸라닭 같이 주문하실 분!", "가톨릭대 정문 앞", "3시 00분 주문", 10))
            add(OrderData("버거킹 같이 시키실 분 구합니다 ~", "동덕여대 인문관 앞", "3시 30분 주문", 10))
            add(OrderData("김밥천국 배달 메이트 구해요", "할리스 한양대점 앞", "2시 10분 주문", 5))
        }
        orderRVAdapter = OrderRVAdapter(orderList)
        viewBinding.orderList.adapter = orderRVAdapter

        orderRVAdapter.setItemClickListener(object: OrderRVAdapter.OnItemClickListener{
            override fun onItemClick(order: OrderData) {
                // 공고 상세 페이지 이동
            }
        })



        orderRVAdapter.notifyDataSetChanged()
    }

    private fun initReview() {
        reviewList.apply {
            add(ReviewData("친절하고 약속을 잘 지켜서 좋았어요!"))
            add(ReviewData("좋았습니당"))
            add(ReviewData("굿굿"))
            add(ReviewData("친절해요"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}