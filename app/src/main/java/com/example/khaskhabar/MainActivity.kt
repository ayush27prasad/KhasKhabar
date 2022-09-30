package com.example.khaskhabar
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), KhabarKholo {

    private lateinit var mAdapter: KhabarListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.rcView).layoutManager=LinearLayoutManager(this)
        fetchData()
        mAdapter = KhabarListAdapter(this)
        findViewById<RecyclerView>(R.id.rcView).adapter = mAdapter
    }
    private fun fetchData() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                              val khabarJSONArray = it.getJSONArray("articles")
                              val khabarArray = ArrayList<Khabar>()
                              for(i in 0 until khabarJSONArray.length()){
                                  val khabarJsonObject = khabarJSONArray.getJSONObject(i)
                                  val khabar = Khabar(
                                      khabarJsonObject.getString("title"),
                                      khabarJsonObject.getString("author"),
                                      khabarJsonObject.getString("url"),
                                      khabarJsonObject.getString("urlToImage")
                                  )
                                  khabarArray.add(khabar)
                              }
                mAdapter.updateKhabar(khabarArray)

            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Khabar) {
        Toast.makeText(this,"खबर पेश करते हुए",Toast.LENGTH_SHORT).show()
        val builder =CustomTabsIntent.Builder()
        val customTabsIntent =builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
            }
}