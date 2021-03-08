package com.example.step18googlemap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // id 가 map 인 프레그먼트의 참조값을 얻어와서 사용할 준비하기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText input_lat=findViewById(R.id.input_lat);
        EditText input_lon=findViewById(R.id.input_lon);
        Button moveBtn=findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 문자열   .trim() 불필요한 공백 제거
                String strLat=input_lat.getText().toString().trim();
                String strLon=input_lon.getText().toString().trim();
                double lat=0;
                double lon=0;
                try {
                    //double type 숫자로 변환
                    lat = Double.parseDouble(strLat);
                    lon = Double.parseDouble(strLon);
                }catch (NumberFormatException ne){//잘못된 숫자 형식이면
                    Toast.makeText(MapsActivity.this,
                            "숫자형식으로 입력하세요!", Toast.LENGTH_SHORT).show();
                    return; //메소드를 여기서 끝내라
                }
                //이동할 목적지 위도경도 객체 37.50498149329903, 127.00492088395313
                LatLng location=new LatLng(lat, lon);
                //카메라 업데이트 객체를 얻어내서
                CameraUpdate cu= CameraUpdateFactory.newLatLngZoom(location, 18);
                //카메라 이동하기 위한 객체를 이용해서 실제로 애니메이션 효과와 함께 이동하기
                mMap.animateCamera(cu);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // GoogleMap 이 준비 완료되면 호출되는 메소드
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //카메라가 비출 위치 (위도, 경도)
        // 에이콘 아카데미 : 37.49847775112994, 127.03047426015019
        // 이우석 : 37.42788032236273, 127.10239180082979

        //위도경도 객체에 위치를 표시할 위도 경도를 생성자의 인자로 전달한다.
        LatLng location = new LatLng(37.49847775112994, 127.03047426015019);
        //지도상에 표시할 마커의 옵션 객체
        MarkerOptions option=new MarkerOptions();
        option.position(location);
        option.title("에이콘 아카데미");
        // res/drawable/austria 이미지를 로딩해서 Drawable 객체 얻어내기
        Drawable icon=getResources().getDrawable(R.drawable.austria);
        //Drawable 을 원하는 크기로 만들어서
        BitmapDescriptor bit=getMarkerIconFromDrawable(icon, 50, 50);
        //마커 옵션에 아이콘으로 지정하기
        option.icon(bit);

        //지도상에 마커 표시하기
        mMap.addMarker(option);
        //원하는 위치로 카메라 이동하기 위한 객체
        CameraUpdate cu= CameraUpdateFactory.newLatLngZoom(location, 18);
        //카메라 이동하기 위한 객체를 이용해서 실제로 애니메이션 효과와 함께 이동하기
        //mMap.animateCamera(cu);
        mMap.moveCamera(cu); //애니매이션 없이 바로 이동하기
    }

    //인자로 전달한 Drawable 객체를 이용해서 BitmapDescriptor 객체를 리턴해주는 메소드
    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable, int width, int height) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}