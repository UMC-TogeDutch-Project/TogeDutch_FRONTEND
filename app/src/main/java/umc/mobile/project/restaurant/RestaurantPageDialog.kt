package umc.mobile.project.restaurant

//import android.app.Dialog
//import android.content.Context
//import android.view.Window
//import android.widget.ImageButton
//import umc.mobile.project.R
//import umc.mobile.project.databinding.ActivityRestaurantPageBinding
//import umc.mobile.project.restaurant.Auth.NaverApi.NaverData
//
//class RestaurantPageDialog(context: Context, ) {
//        lateinit var viewBinding: ActivityRestaurantPageBinding
//        private val dlg = Dialog(context)
//        private lateinit var btn_back : ImageButton
//    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
//    private lateinit var restaurantImgRVAdapter: RestaurantImgRVAdapter
////    var result = ArrayList<NaverData.NaverSearchData>()
//
//    fun start(){
//            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dlg.setContentView(R.layout.activity_restaurant_page)
//            dlg.setCanceledOnTouchOutside(false)       //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 설정
//            dlg.setCancelable(true)    // 취소가 가능하도록 하는 코드
//
//            btn_back = dlg.findViewById(R.id.back_btn)
//
//            btn_back.setOnClickListener {
//                dlg.dismiss()
//            }
//
//
//            dlg.show()
//        }
//
//    }
