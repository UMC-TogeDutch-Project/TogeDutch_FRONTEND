import org.json.JSONException
import org.json.JSONObject
import umc.mobile.project.RestaurantFragment
import umc.mobile.project.restaurant.Auth.NaverApi.NaverData
import umc.mobile.project.restaurant.blog.BlogData
import umc.mobile.project.searchResult

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class NaverBlogAPI {
    var blogList = ArrayList<BlogData>()

    fun main(searchTitle: NaverData.NaverTitle): ArrayList<BlogData> {
        val clientId = "wmV0kYp4ek0ba6kCCbxB"
        val clientSecret = "PgxJ1saGO6"
        var text: String? = null
        text = try {
            URLEncoder.encode(searchTitle.title, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }
        val apiURL =
            "https://openapi.naver.com/v1/search/blog?query="  + text!! + "&display=5"  // JSON 결과

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

    private fun parseData(responseBody: String): ArrayList<BlogData> {

        var title: String
        var link: String
        var description: String
        var name: String
        var date: String
        var jsonObject: JSONObject? = null

        var bw = BufferedWriter(OutputStreamWriter(System.out))

        try {
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject.getJSONArray("items")

//            for (i in 0 until jsonArray.length()) {
//                val item = jsonArray.getJSONObject(i)
//                Titles[i] = item.getString("title")
//            }


            for (i in 0 until jsonArray.length()) {

                val item = jsonArray.getJSONObject(i)

                val blogData = BlogData(
                    item.getString("title"),
                    item.getString("postdate"),
                    item.getString("bloggername"),
                    item.getString("link")
                )
                blogList.add(blogData)

                title = item.getString("title")
                link = item.getString("link")
                description = item.getString("description")
                name = item.getString("bloggername")
                date = item.getString("postdate")
                bw.write("TITLE : $title LINK : $link DESCRIPTION : $description NAME : $name DATE : $date\n");

            }
            bw.flush()
            bw.close()


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return blogList
    }

}