package com.example.newsfresh

//import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.JsonReader
//import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
//import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.transform.ErrorListener

//import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClikcd {

    private lateinit var mAdaptar: NewsListAdaptar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recylerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        //val madapter = NewsListAdaptar( this)
        recylerView.adapter = mAdaptar
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=1466de10738d42b2afed5fde35958736"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {

            val newsJasonArray = it.getJSONArray("articles")
            val newsArray = ArrayList<News>()
            for(i in 0 until newsJasonArray.length()){
                val newsJasonObject = newsJasonArray.getJSONObject(i)
                val news = News(
                    newsJasonObject.getString("title"),
                    newsJasonObject.getString("author"),
                    newsJasonObject.getString("url"),
                    newsJasonObject.getString("urlToImage")
                )
                newsArray.add(news)
            }
            mAdaptar.updateNews(newsArray)


            },
            Response.ErrorListener{

            }

        )




                    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}