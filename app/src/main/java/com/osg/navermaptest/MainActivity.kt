package com.osg.navermaptest

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.ui.tooling.preview.Preview
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.osg.navermaptest.ui.NaverMapTestTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            NaverMapTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MapView(callback)
                }
            }
        }

    }

    val callback = OnMapReadyCallback {
        it.mapType = NaverMap.MapType.Basic

        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.map = it

        val path = PathOverlay()
        path.coords = listOf(
            LatLng(37.57152, 126.97714),
            LatLng(37.56607, 126.98268),
            LatLng(37.56445, 126.97707),
            LatLng(37.55855, 126.97822)
        )

        path.map = it
    }

//    override fun onMapReady(p0: NaverMap) {
//        p0.mapType = NaverMap.MapType.Hybrid
//    }

}

@Composable
fun MapView(callback: OnMapReadyCallback) {
    val context = ContextAmbient.current
    val customView = remember{
        MapView(context).apply {
        }
    }

    Column {
        searchAddress()
        AndroidView(viewBlock = {customView}){view->
            view.getMapAsync(callback)
        }
    }
}

@Composable
fun searchAddress(){
    val context = ContextAmbient.current
    val textState = remember { mutableStateOf(TextFieldValue())}
    Row{
        TextField(
            value = textState.value,
            onValueChange = {textState.value = it}
        )
        Button(onClick = { onSubmit(textState, context = context) }) {
            Text(text = "검색")
        }
    }
}

// 검색창에 입력 후 버튼을 누를 때
// 텍스트 읽어오기, 
fun onSubmit(text: MutableState<TextFieldValue>, context: Context){
    Toast.makeText(context, text.value.text, Toast.LENGTH_SHORT).show()
}

