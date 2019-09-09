package com.greentea.kotlintest1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
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

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var naverMap: NaverMap
    lateinit var infoWindow: InfoWindow

//    lateinit var map: HashMap<String, String>
    val map: HashMap<String, String> = hashMapOf("1" to "restaurant1", "2" to "restaurant2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment ?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

//        map["1"] = "restaurant1"
//        map["2"] = "restaurant2"

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        this.naverMap = naverMap

        infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this.applicationContext) {

//            override fun getView(infoWindow: InfoWindow): View {
//                return map[]
//            }

            override fun getText(infoWindow: InfoWindow): CharSequence {
                return map[infoWindow.marker?.tag] as CharSequence? ?: ""
//                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }

        val marker1 = Marker()
        marker1.position = LatLng(37.5670135, 126.9783740)
        marker1.map = naverMap
        marker1.tag = "1"

        var view: ImageView = ImageView(this.applicationContext)

//        Glide.with(this.applicationContext)
//            .load("https://img.siksinhot.com/find/1458715763440006.jpg?w=307&h=300&c=Y")
//            .into(view)

        marker1.icon = OverlayImage.fromView(view)
        //marker1.icon = OverlayImage.fromPath("https://img.siksinhot.com/find/1458715763440006.jpg?w=307&h=300&c=Y")
        click_marker(marker1)

        val marker2 = Marker()
        marker2.position = LatLng(37.5670135, 126.9833740)
        marker2.map = naverMap
        marker2.tag = "2"
        //marker2.icon = OverlayImage.fromPath("https://img.siksinhot.com/find/1458715839558010.jpg?w=307&h=300&c=Y")
        click_marker(marker2)
    }

    fun click_marker(marker: Marker){

        marker.setOnClickListener {
            if(marker.infoWindow == null){
                infoWindow.open(marker)
            }
            else infoWindow.close()

            true
        }
    }
}
