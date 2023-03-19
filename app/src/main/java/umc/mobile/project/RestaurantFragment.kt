package umc.mobile.project
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import umc.mobile.project.announcement.AnnounceDetailActivity
import umc.mobile.project.announcement.AnnounceListActivity
//import umc.mobile.project.chat.ChatRoom
//import umc.mobile.project.chat.ChattingActivity
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.restaurant.Auth.NaverApi.*
import umc.mobile.project.restaurant.RestaurantImgRVAdapter
import umc.mobile.project.restaurant.RestaurantRVAdapter
import umc.mobile.project.restaurant.blog.RestaurantPageActivity
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


class RestaurantFragment : Fragment() {
    var Titles: Array<String?> = arrayOfNulls<String>(5)

    val clientId = "wmV0kYp4ek0ba6kCCbxB"
    val clientSecret = "PgxJ1saGO6"

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var restaurantRVAdapter: RestaurantRVAdapter
    private lateinit var restaurantImgRVAdapter: RestaurantImgRVAdapter


    var handler= Handler(Looper.getMainLooper())

    var naverList = ArrayList<NaverData.NaverSearchData>()
    var naverImgList = ArrayList<NaverData.NaverImgData>()
    var naverTitle = ArrayList<NaverData.NaverTitle>()

    var result = ArrayList<NaverData.NaverSearchData>()
    var resultImage = ArrayList<NaverData.NaverImgData>()
    var resultT = ArrayList<NaverData.NaverTitle>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)





        val thread = Thread {
            var naverPlaceSearch = RestaurantFragment()
            result =  naverPlaceSearch.main()


            Log.d("naver", result.toString())
            activity?.runOnUiThread {
                restaurantRVAdapter = RestaurantRVAdapter(result)
                binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
                binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결


                restaurantRVAdapter.notifyDataSetChanged()


            }
        }.start()



        return binding.root
    }




    private fun initRecyclerView() {

//        restaurantRVAdapter = RestaurantRVAdapter(result)
//        binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
//        binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결
//        mRestaurnatData.apply {
//            add(RestaurantData("파이프그라운드 ", "서울 용산구 한남대로27길 66 지하1층", "02-4948-3929", "4.1", R.drawable.img1))
//            add(RestaurantData("꽁티드툴레아 ", "서울 강남구 도산대로49길 39", "02-4938-2939", "3.3", R.drawable.img2))
//            add(RestaurantData("세상의모든아침 여의도점 ", "서울 영등포구 여의대로 24 전경련회관 50층, 51층", "02-4756-3872", "4.3", R.drawable.img3))
//            add(RestaurantData("땀땀 ", "서울 강남구 강남대로98길 12-5", "02-7663-8883", "3.5",R.drawable.img4))
//            add(RestaurantData("애플하우스 ", "서울 동작구 동작대로27다길 29 2층", "02-8839-9288", "4.0",R.drawable.img5))
//            add(RestaurantData("까폼 ", "서울 강남구 선릉로153길 18 지하1층", "02-3229-1182", "4.1",R.drawable.img6))
//            add(RestaurantData("Summer Lane ", "서울 용산구 이태원로55가길 49 1층 summerlane", "02-8837-2211", "3.3",R.drawable.img7))
//
//        }
//        Toast.makeText(context, "구글 주변장소 성공.", Toast.LENGTH_SHORT).show()



//        restaurantRVAdapter.setItemClickListener(object : RestaurantRVAdapter.OnItemClickListener {
//            override fun onItemClick(restaurantData: Result) {
//                val dlg = context?.let { RestaurantPageDialog(it) }
//                if (dlg != null) {
//                    dlg.start()
//                }
////                Intent(context, RestaurantPageActivity::class.java).apply {
////                    putExtra("data", mRestaurnatData)
////                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////                }.run { context?.startActivity(this) }
//            }
//        })

    }

    fun main(): ArrayList<NaverData.NaverSearchData> {
        var text: String? = null

        try {
            text = URLEncoder.encode("익선동 맛집", "UTF-8")    // 검색어
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL =
            "https://openapi.naver.com/v1/search/local.json?query=" + text!! + "&display=10"   // json 결과

        val requestHeaders: HashMap<String, String> = HashMap()
        requestHeaders.put("X-Naver-Client-Id", clientId)
        requestHeaders.put("X-Naver-Client-Secret", clientSecret)


        val responseBody = get(apiURL, requestHeaders)
        val result = parseData(responseBody)
        return result
    }

    private operator fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val con = connect(apiUrl)

        try {
            con.requestMethod = "GET"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                readBody(con.inputStream)
            } else { // 에러 발생
                readBody(con.errorStream)
            }

        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        try {
            val url = URL(apiUrl)
            return url.openConnection() as HttpURLConnection
        } catch (e: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
        } catch (e: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl", e)
        }

    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()

                var line: String? = lineReader.readLine()
                while (line != null) {
                    responseBody.append(line)
                    line = lineReader.readLine()
                }
                return responseBody.toString()
            }
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }


    private fun parseData(responseBody: String): ArrayList<NaverData.NaverSearchData> {

        var title: Array<String?> = arrayOfNulls<String>(5)
        var category: String
        var description: String
        var address: String
        var jsonObject: JSONObject? = null

        var bw = BufferedWriter(OutputStreamWriter(System.out))

        try {
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject.getJSONArray("items")
            for (i in 0 until jsonArray.length()) {

                val item = jsonArray.getJSONObject(i)
                val naverData = NaverData.NaverSearchData(
                    item.getString("title"),
                    item.getString("category"),
                    item.getString("description"),
                    item.getString("address")
                )
                naverList.add(naverData)
//                val naverDataT = NaverData.NaverTitle(
//                    item.getString("title")
//                )
//                naverTitle.add(naverDataT)
                Titles[i] = item.getString("title")
                title[i] = item.getString("title")
                category = item.getString("category")
                description = item.getString("description")
                address = item.getString("address")
                bw.write("TITLE : " + title[i] + " CATEGORY : $category DESCRIPTION : $description ADDRESS : $address \n");

            }
            bw.flush()
            bw.close()

            for(i in 0 until jsonArray.length()){
                val thread = Thread {
                    var naverImageSearch = NaverImageSearchAPI()
                    naverImgList = naverImageSearch.main(title[i])
                    Log.d("이미지 리스트", naverImgList.toString())
                    Log.d("스레드 안 title", Titles.toString())

//                    handler.post{
//                        restaurantImgRVAdapter = RestaurantImgRVAdapter(naverImgList)
//                        binding.rvRes.adapter = restaurantImgRVAdapter //리사이클러뷰에 어댑터 연결
//                        binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결
//
//
//                        restaurantImgRVAdapter.notifyDataSetChanged()
//                    }
                }.start()

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("titles", Titles.toString())
        return naverList
    }


}