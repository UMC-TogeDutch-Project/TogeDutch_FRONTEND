package umc.mobile.project.restaurant.Auth.NaverApi

import org.json.JSONException
import org.json.JSONObject
import umc.mobile.project.restaurant.RestaurantRVAdapter
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


class NaverPlaceSearchAPI{
    val clientId = "wmV0kYp4ek0ba6kCCbxB"
    val clientSecret = "PgxJ1saGO6"
    var naverList = ArrayList<NaverSearchData>()
    fun main(): ArrayList<NaverSearchData> {
        var text: String? = null

        try {
            text = URLEncoder.encode("안암동 맛집", "UTF-8")    // 검색어
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


    private fun parseData(responseBody: String): ArrayList<NaverSearchData> {
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
                val naverData = NaverSearchData(
                    item.getString("title"),
                    item.getString("category"),
                    item.getString("description"),
                    item.getString("address")
                )
                naverList.add(naverData)

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
                    naverImageSearch.main(title[i])
                }.start()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return naverList
    }

}