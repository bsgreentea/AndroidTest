package com.greentea.kotlintest1

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.info.view.*
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var naverMap: NaverMap
    lateinit var infoWindow: InfoWindow

//    lateinit var map: HashMap<String, String>
    val map: HashMap<String, String> = hashMapOf("1" to "restaurant1", "2" to "restaurant2")
    val mapImg: HashMap<String, String> = hashMapOf("1" to "https://img.siksinhot.com/article/1568005867240255.jpg",
        "2" to "https://img.siksinhot.com/article/1567578557349225.jpeg")
    val mapUrl: HashMap<String, String> = hashMapOf("1" to "https://www.siksinhot.com/theme/magazine/2213",
        "2" to "https://www.siksinhot.com/theme/magazine/2217")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment ?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        this.naverMap = naverMap

        infoWindow = InfoWindow()

//        naverMap.setOnMapClickListener {a,b->
//            infoWindow.close()
//        }

        // how to adapt custom view
        infoWindow.adapter = object :InfoWindow.DefaultViewAdapter(this.applicationContext){

            override fun getContentView(infoWindow: InfoWindow): View {

                val idx = infoWindow.marker?.tag
                val info = map[idx]
                val img_url = mapImg[idx]
                val go_url = mapUrl[idx]

                var view = View.inflate(applicationContext, R.layout.info, null)

                view.text_view.setText(info)
//                view.image_view.setImageURI(Uri.parse(url))

                Glide.with(applicationContext).load(img_url).into(view.image_view)

//                view.image_view.setOnClickListener{
//                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(go_url))
//                    startActivity(i)
//                }

                return view
            }
        }

//        infoWindow.setOnClickListener{
//            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.siksinhot.com/theme/magazine/2217"))
//            startActivity(i)
//        }

        // way to set string
//        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this.applicationContext) {
//
//            override fun getText(infoWindow: InfoWindow): CharSequence {
//                return map[infoWindow.marker?.tag] as CharSequence? ?: ""
//                return infoWindow.marker?.tag as CharSequence? ?: ""
//            }
//        }

        val marker1 = Marker()
        marker1.position = LatLng(37.5670135, 126.9783740)
        marker1.map = naverMap
        marker1.tag = "1"

        val marker2 = Marker()
        marker2.position = LatLng(37.5670135, 126.9833740)
        marker2.map = naverMap
        marker2.tag = "2"

        val marker_listener = Overlay.OnClickListener { overlay ->
            val marker = overlay as Marker

            if(marker.infoWindow == null){
                infoWindow.open(marker)
            }
            else{
                infoWindow.close()
            }

            true
        }

        marker1.onClickListener = marker_listener
        marker2.onClickListener = marker_listener
    }
}
