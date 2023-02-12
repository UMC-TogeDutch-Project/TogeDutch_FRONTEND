package umc.mobile.project.mypage.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemReviewBinding


class ReviewRVAdapter (private val reviewList: ArrayList<ReviewData>) : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {
    val TAG: String = "로그"

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReviewBinding = ItemReviewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        Log.d(TAG, "reviewRVadapter로 넘어왔다")
        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = reviewList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position])

    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: ReviewData) {
            binding.reviewText.text = application.review
            Log.d("review: ", application.review)
        }
    }

}