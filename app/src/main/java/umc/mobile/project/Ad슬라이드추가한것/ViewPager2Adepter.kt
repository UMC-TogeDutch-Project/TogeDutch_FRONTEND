package umc.mobile.project.Ad슬라이드추가한것

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.mobile.project.R

class ViewPager2Adepter(private val context: Context, private val adList: MutableList<Ad> ): RecyclerView.Adapter<ViewPager2Adepter.PagerViewHolder>() {

    /**
     * View 를 담을 ViewHolder class 를 정의 한다.
     */
    inner class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.ad_title)
        val message : TextView = itemView.findViewById(R.id.ad_message)
        val image: ImageView = itemView.findViewById(R.id.ad_image)
    }

    /**
     * ViewHolder 를 인스턴스화 하고 return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_ad_page,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    /**
     * 뷰와 데이터를 바인딩 하는 메서드
     */
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.title.text = adList[position].store
        holder.message.text = adList[position].request
        Glide.with(context).load(adList[position].image).into(holder.image)
        holder.itemView.setOnClickListener {
            val ad_dialog = AdDetailDialog(context)
            ad_dialog.start()
        }
    }

    override fun getItemCount() = adList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}