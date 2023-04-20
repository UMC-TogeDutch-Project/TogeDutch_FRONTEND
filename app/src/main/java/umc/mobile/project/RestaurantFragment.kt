package umc.mobile.project
//import umc.mobile.project.chat.ChatRoom
//import umc.mobile.project.chat.ChattingActivity


import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONException
import org.json.JSONObject
import umc.mobile.project.databinding.FragmentRestaurantBinding
import umc.mobile.project.restaurant.Auth.NaverApi.*
import umc.mobile.project.restaurant.RestaurantRVAdapter
import umc.mobile.project.restaurant.blog.BlogData
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

//주변맛집에 필요한 위도경도
var latitude = 50.02
var longitude = 60.02

class RestaurantFragment : Fragment() {
    var Titles: Array<String?> = arrayOfNulls<String>(5)
    var Titles2: Array<String?> = arrayOfNulls<String>(5)

    val clientId = "wmV0kYp4ek0ba6kCCbxB"
    val clientSecret = "PgxJ1saGO6"


    private lateinit var binding: FragmentRestaurantBinding

    private lateinit var restaurantRVAdapter: RestaurantRVAdapter


    var naverList = ArrayList<NaverData.NaverSearchData>()
    var naverImgList = ArrayList<NaverData.NaverImgData>()

    var naverTitleList = ArrayList<NaverData.NaverTitle>()
    var result = ArrayList<NaverData.NaverSearchData>()
    var resultT = ArrayList<NaverData.NaverTitle>()
    var naverBlog = ArrayList<BlogData>()
    var searchResult = "연남동 맛집"
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentRestaurantBinding.inflate(inflater, container, false)



        binding.searchBtn.setOnClickListener{
            searchResult = binding.resSearchPost.text.toString() + " 맛집"
//            val ft: FragmentTransaction = this.requireFragmentManager().beginTransaction()
//            ft.detach(this).attach(this).commit()
            val fragment = RestaurantFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_restaurant, fragment)
            transaction.commit()
        }


        val thread = Thread {
            searchResult = context?.let { defaultAddress(it) }.toString()
            Log.d("스레드에서 searchResult", searchResult)
            var naverPlaceSearch = RestaurantFragment()
            result =  naverPlaceSearch.main(searchResult)

            resultT =  naverPlaceSearch.main2(searchResult)
            Log.d("resultT", resultT.toString())
            var naverImageSearch = NaverImageSearchAPI()
            Log.d("resultT 입니당", naverImageSearch.main(resultT[0]).toString())
            Log.d("resultT 입니당", naverImageSearch.main(resultT[1]).toString())
            Log.d("resultT 입니당", naverImageSearch.main(resultT[2]).toString())
            Log.d("resultT 입니당", naverImageSearch.main(resultT[3]).toString())

            naverImgList = naverImageSearch.main(resultT[4])
            Log.d("naver", result.toString())
            activity?.runOnUiThread {

                restaurantRVAdapter = RestaurantRVAdapter(result, naverImgList)
                binding.rvRes.adapter = restaurantRVAdapter //리사이클러뷰에 어댑터 연결
                binding.rvRes.layoutManager = LinearLayoutManager(context) //레이아웃 매니저 연결


                restaurantRVAdapter.notifyDataSetChanged()

            }
        }.start()


        return binding.root
    }

    //회원가입시 저장한 위도 경도 가지고 주소 변환
    fun defaultAddress(context: Context):String{
        val geocoder = Geocoder(context, Locale.KOREAN)
        val addressList = geocoder.getFromLocation(latitude, longitude, 1)
        var dongAddress = ""
        if (addressList.isNotEmpty()) {
            val address = addressList[0]
            val city = address.locality
            val state = address.adminArea
            val country = address.countryName
            val addressLine = address.getAddressLine(0)
            val fullAddress = "$addressLine, $city, $state, $country"

            val pattern = Pattern.compile("(\\S+[동|가|로|길])")
            val matcher = pattern.matcher(fullAddress)

            if (matcher.find()) {
                 dongAddress = matcher.group()
                // dongAddress에 "동" 저장
                dongAddress += " 맛집"



            }
            else println("실패")




        }
        return dongAddress
    }




    fun main(searchResult: String): ArrayList<NaverData.NaverSearchData> {
        var text: String? = null


        try {
            text = URLEncoder.encode(searchResult, "UTF-8")    // 검색어
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL =
            "https://openapi.naver.com/v1/search/local.json?query=" + text!! + "&display=5"   // json 결과

        val requestHeaders: HashMap<String, String> = HashMap()
        requestHeaders.put("X-Naver-Client-Id", clientId)
        requestHeaders.put("X-Naver-Client-Secret", clientSecret)


        val responseBody = get(apiURL, requestHeaders)
        val result = parseData(responseBody)
        return result
    }


    fun main2(searchResult: String): ArrayList<NaverData.NaverTitle> {
        var text: String? = null

        try {
            text = URLEncoder.encode(searchResult, "UTF-8")    // 검색어
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL =
            "https://openapi.naver.com/v1/search/local.json?query=" + text!! + "&display=5"   // json 결과

        val requestHeaders: HashMap<String, String> = HashMap()
        requestHeaders.put("X-Naver-Client-Id", clientId)
        requestHeaders.put("X-Naver-Client-Secret", clientSecret)


        val responseBody = get(apiURL, requestHeaders)
        val result = parseData2(responseBody)
        return result
    }


    private fun parseData2(responseBody: String): ArrayList<NaverData.NaverTitle> {

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

                val naverData = NaverData.NaverTitle(
                    item.getString("title")
                )
                naverTitleList.add(naverData)
                Titles[i] = item.getString("title").replace(Regex("<b>"),"").replace(Regex("</b>"),"")
                title[i] = item.getString("title")
                category = item.getString("category")
                description = item.getString("description")
                address = item.getString("address")
                bw.write("TITLE : " + title[i] + " CATEGORY : $category DESCRIPTION : $description ADDRESS : $address \n");

            }
            bw.flush()
            bw.close()


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d("titles", Titles.toString())
        Log.d("title0", Titles[0].toString())

        return naverTitleList
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

//            for (i in 0 until jsonArray.length()) {
//                val item = jsonArray.getJSONObject(i)
//                Titles[i] = item.getString("title")
//            }


            for (i in 0 until 5) {

                val item = jsonArray.getJSONObject(i)

                val naverData = NaverData.NaverSearchData(
                    item.getString("title").replace(Regex("<b>"),"").replace(Regex("</b>"),""),
                    item.getString("category").replace(Regex(">"),"-"),
                    item.getString("description"),
                    item.getString("address")
                )
                naverList.add(naverData)

                title[i] = item.getString("title").replace(Regex("<b>"),"").replace(Regex("</b>"),"")
                category = item.getString("category")
                description = item.getString("description")
                address = item.getString("address")
                bw.write("TITLE : " + title[i] + " CATEGORY : $category DESCRIPTION : $description ADDRESS : $address \n");

            }
            bw.flush()
            bw.close()


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return naverList
    }


}