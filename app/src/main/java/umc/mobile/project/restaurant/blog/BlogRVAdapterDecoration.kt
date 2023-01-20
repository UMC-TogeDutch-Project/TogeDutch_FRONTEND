package umc.mobile.project.restaurant.blog

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BlogRVAdapterDecoration (private val divHeight : Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state : RecyclerView.State) {

        super.getItemOffsets(outRect, view,parent, state )
        outRect.top = divHeight
        outRect.bottom = divHeight
    }
}