package umc.mobile.project

import android.content.Context
import Post
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.announcement.AnnounceAlertDialog
import umc.mobile.project.announcement.AnnounceAlertDialogInterface
import umc.mobile.project.announcement.Auth.LikePost.LikePostResult
import umc.mobile.project.announcement.Auth.LikePost.LikePostService
import umc.mobile.project.announcement.Auth.LikePost.Result
import umc.mobile.project.databinding.ItemDataBinding
import umc.mobile.project.ram.Geocoder_location
import umc.mobile.project.ram.my_application_1.post_id_to_detail
import umc.mobile.project.ram.my_application_1.user_id_logined
import umc.mobile.project.ram.my_application_1.user_id_var
import umc.mobile.project.wishlist.GetLikePost.LikePostGetResult
import umc.mobile.project.wishlist.GetLikePost.LikePostGetService


class DataImminentRVAdapter(private val homeDataList: ArrayList<Post>) : RecyclerView.Adapter<DataImminentRVAdapter.ImminentViewHolder>(),
    LikePostGetResult {

    private val checkboxStatus = SparseBooleanArray()
    lateinit var context : Context

    private var contentPostId = 0

    private val clickedLikeStatus = arrayListOf<Post>()

    //ViewHolder 객체
   inner class ImminentViewHolder(private val viewBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root), LikePostResult, AnnounceAlertDialogInterface {

        fun bind(homeData: Post) {
            Glide.with(context).load(homeData.image).centerCrop().into(viewBinding.ivItemImageThird)
            viewBinding.tvItemTitle.text = homeData.title //제목

            var latLong_to_address : String = Geocoder_location().calculate_location(context, homeData.latitude, homeData.longitude)
            var txt_location = latLong_to_address
            viewBinding.tvItemWhere.text = txt_location // 주소

            val txt_time = homeData.order_time
//            2022-01-23T03:34:56.000+00:00
            var txt_hour = txt_time.substring(11 until 13)
            var txt_minute = txt_time.substring(14 until 16)
            var txt_time_substring = txt_hour+"시" + txt_minute + "분 주문"

            viewBinding.tvItemTime.text = txt_time_substring // 주문한 시간
            viewBinding.annApp.text = homeData.recruited_num.toString() // 신청인원
            viewBinding.annRecruit.text = homeData.num_of_recruits.toString() //총 인원

            contentPostId = homeData.post_id

            Log.d("absoluteAdapterPosition: ", absoluteAdapterPosition.toString())

            viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon)

            var id = clickedLikeStatus.find { it.post_id == homeData.post_id }
            if(id != null) {
                Log.d("체크된 post 제목 ======================== ", homeData.title)
                viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon_fill)
            }


            viewBinding.btnLikeThird.setOnClickListener {
//                viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon_fill)
                viewBinding.btnLikeThird.isSelected = !viewBinding.btnLikeThird.isSelected

                if(viewBinding.btnLikeThird.isSelected) {
                    viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon_fill)
                } else {
                    viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon)
                }
                notifyDataSetChanged()

                val likePostService = LikePostService()
                likePostService.setLikePostResult(this)
                likePostService.sendLike(user_id_logined, homeData.post_id)
                Log.d("post_id: ", homeData.post_id.toString())

            }

        }


        override fun LikePostSuccess(result: Result) {
            viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon_fill)
            notifyDataSetChanged()
            Log.d("관심목록 등록 성공", "")
            Log.d("post_id: ", result.postIdx.toString())
            Log.d("likeIdx: ", result.likeIdx.toString())
            Log.d("post_User_userIdx: ", result.post_User_userIdx.toString())
            Log.d("like_userIdx: ", result.like_userIdx.toString())
            Toast.makeText(context, "관심 공고 등록 성공.", Toast.LENGTH_SHORT).show()
        }

        override fun LikePostFailureMyPost() {
            viewBinding.btnLikeThird.setBackgroundResource(R.drawable.main_item_heart_icon)
            val dlg = AnnounceAlertDialog(context, this)
            dlg.start5()
        }

        override fun LikePostFailureAdd() {
            val dlg = AnnounceAlertDialog(context, this)
            dlg.start6()
        }

        override fun btnFinish() {

        }

    }

    //ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataImminentRVAdapter.ImminentViewHolder {
        val viewBinding: ItemDataBinding = ItemDataBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false)

        context = viewGroup.context
        getLikePost()

        return ImminentViewHolder(viewBinding)
    }

    private fun getLikePost() {
        val likePostGetService = LikePostGetService()
        likePostGetService.setLikePostGetResult(this)
        likePostGetService.getLikePost(user_id_logined)
    }

    override fun getPostUploadSuccess(code: Int, result: ArrayList<Post>) {
        clickedLikeStatus.addAll(result)
        for(i in 0..result.size-1)
            Log.d("title : ", result[i].title)
    }

    override fun getPostUploadFailure(code: Int, message: String) {
        Log.d("", "")
    }

    //ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataImminentRVAdapter.ImminentViewHolder, position: Int) {
        if(holder is ImminentViewHolder) {
            holder.bind(homeDataList[position])
            post_id_to_detail = homeDataList[position].post_id

            holder.itemView.setOnClickListener {
                user_id_var = homeDataList[position].user_id
                Log.d("user_id : ===========", user_id_var.toString())
                itemClickListener.onItemClick(homeDataList[position])
                notifyItemChanged(position)
            }
        }
    }

    //표현할 아이템의 총 갯수
    override fun getItemCount(): Int = if (homeDataList.isNullOrEmpty()) 0 else homeDataList.size

    //1
    interface OnItemClickListener {
        fun onItemClick(homeData: Post)

    }

    fun setItemClickListener(onItemClickListener1: OnItemClickListener) {
        this.itemClickListener = onItemClickListener1
    }

    private lateinit var itemClickListener : OnItemClickListener

}
