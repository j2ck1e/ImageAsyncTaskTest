package com.jcdesign.imageasynctasktest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btn: Button
    private lateinit var bitmap: Bitmap
    val url = "https://a-z-animals.com/media/2022/09/shutterstock_38399851-1536x922.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.iv_result_image)
        btn = findViewById(R.id.btn_download_image)


        btn.setOnClickListener {
            val task = DownloadImageTask()
            try {
                bitmap = task.execute(url).get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            imageView.setImageBitmap(bitmap)
        }
    }


    class DownloadImageTask : AsyncTask<String, Unit, Bitmap>() {
        override fun doInBackground(vararg params: String?): Bitmap {
            lateinit var urlConnection: HttpURLConnection
            lateinit var bitmap: Bitmap
            try {
                val url = URL(params[0])
                urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
            return bitmap
        }
    }
}
