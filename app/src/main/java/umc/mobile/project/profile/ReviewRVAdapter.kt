package umc.mobile.project.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.mobile.project.databinding.ItemReviewBinding


class ReviewRVAdapter (private val reviewList: ArrayList<ReviewData>) : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    // 보여줄 아이템 개수만큼 View를 생성 (RecyclerView가 초기화 될 때 호출)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ReviewRVAdapter.ViewHolder {
        val binding: ItemReviewBinding = ItemReviewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    // 보여줄 아이템 개수
    override fun getItemCount(): Int = reviewList.size

    // 생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: ReviewRVAdapter.ViewHolder, position: Int) {
        holder.bind(reviewList[position])

    }

    // ViewHolder 단위 객체로 View의 데이터를 설정
    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: ReviewData) {
            binding.reviewText.text = application.review

        }
    }

}