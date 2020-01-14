package com.example.myapplication.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;
import com.example.myapplication.google_map.Location_cus;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragExample2 extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private Context mContext;
//    SharedPreferences pref = requireContext().getSharedPreferences("pref",Context.MODE_PRIVATE);
//    String accountName = pref.getString("key1", "hi");

    TextView textView1, textView2;
    Button button;
    String ex1 = "카이스트";
    ViewGroup rootView;

    //user database 에 저장된 전체 사용자의 db
    final String[] location = new String[]{"카이스트 소망관", "카이스트 n1","카이스트 성심관", "카이스트 사랑관", "카이스트 도서분관", "카이스트 아름관", "카이스트 진리관", "카이스트 기계동", "카이스트 본관"};
    final String[] mbti = new String[]{"ENFJ", "INFJ", "INTP", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "ENTP"};
    final String[] name = new String[]{"lee", "park", "jung","lee2", "park2", "jung2","lee3", "park3", "jung3"};

    String tmp_mbti="";
    //로그인한 사용자의 MBTI
    //db 에서 가져와야하는 데이터
    String person_mbti = "ENFJ";

    //  Location_cus[] location_ar_cus = new Location_cus[100];


    double cur_lat, cur_long, idx_lat, idx_long;

    int cnt = 0;

    private MapView mapView;
    private GoogleMap mMap;
    LottieAnimationView change_btn;
    SharedPreferences pref;

    ArrayList<Location_cus> arrayList;

    int flag=0;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    TextView user_name, user_mbti,textView_1;
    ImageView user_send_message, user_mbti_img, user_home;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(int i=0; i< location.length; i++){
            Location_cus location_cus = new Location_cus();
            idx_lat = findGeoPoint(getActivity(), location[i]).getLatitude();
            idx_long = findGeoPoint(getActivity(), location[i]).getLongitude();
            if(distance(cur_lat, cur_long, idx_lat, idx_long, "kilometer" ) <2.5){
//                textView2.setText("위도 : " + String.valueOf(idx_lat) + "경도 : "+ String.valueOf(idx_long));
                cnt ++;
                Log.d("count ++", String.valueOf(cnt));
                location_cus.setUser_name(name[i]);
                location_cus.setUser_mbti(mbti[i]);
                location_cus.setLatitude(idx_lat);
                location_cus.setLongitude(idx_long);

                //기준을 만족하는 data 추가
//                location_ar_cus[i] = location_cus;
                arrayList.add(location_cus);
                Log.d("count ++", mbti[i]);
            }
        }
        Log.d("size ar", String.valueOf(arrayList.size()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.activity_frag_ex2,container, false);
        final View custom_dialog = inflater.inflate(R.layout.activity_custom_dialog,rootView.findViewById(R.id.layout_root));


        textView_1 = rootView.findViewById(R.id.textView_1);
        //custom dialog 항목들을 해당 maker 에 맞는 친구들로 바꿔줘야하 한다,
        user_mbti = custom_dialog.findViewById(R.id.user_mbti);
        user_name = custom_dialog.findViewById(R.id.user_name);
        user_send_message = custom_dialog.findViewById(R.id.user_send);
        user_mbti_img = custom_dialog.findViewById(R.id.user_image);
        user_home = custom_dialog.findViewById(R.id.user_home);

        //cutom dialog 설정 해주는 과정
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(custom_dialog);
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        user_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //message 보내는 위치
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
            }
        });

        user_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "hi2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Dialog_event_activity.class);

                //Dialog event activity 로 보내는 데이터,
                intent.putExtra("key_i", tmp_mbti);
                Log.d("send_account_name", tmp_mbti);
                getActivity().startActivity(intent);
            }
        });


        final Geocoder geocoder = new Geocoder(getActivity());
        arrayList=new ArrayList<Location_cus>();

        mapView = (MapView)rootView.findViewById(R.id.google_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        Location_cus location_cus = new Location_cus();

        change_btn = rootView.findViewById(R.id.change_btn);
        change_btn.setAnimation("find.json");
        change_btn.setVisibility(View.VISIBLE);
        change_btn.setEnabled(true);
        change_btn.loop(false);


        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0){
                    mMap.clear();
                    textView_1.setText("나와 비슷한");
                    for(int i=0; i< arrayList.size(); i++){

                        String tmp = arrayList.get(i).getUser_mbti();
                        int check =0;
                        for(int j=0; j<4;j++){
                            if(tmp.charAt(j) == person_mbti.charAt(j) ){
                                check++;
                                Log.d("checking!!!!", String.valueOf(check));
                            }
                        }

                        if(check >2){
                            Log.d("size ar", String.valueOf(arrayList.size()));
                            //버튼 클릭시 원하는 사용자 맵에 추가
                            MarkerOptions markerOptions = new MarkerOptions();
                            idx_lat = arrayList.get(i).getLatitude();
                            idx_long = arrayList.get(i).getLongitude();
                            markerOptions
                                    .position(new LatLng(idx_lat , idx_long))
                                    .title(arrayList.get(i).getUser_name())
                                    .snippet(arrayList.get(i).getUser_mbti())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions);

                            CircleOptions circle2_5KM = new CircleOptions().center(new LatLng(cur_lat , cur_long))
                                    .radius(1500)
                                    .strokeWidth(0f)
                                    .fillColor(0x220000FF);
                            mMap.addCircle(circle2_5KM);
                        }
                    }

                    //현재위치 추가
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions
                            .position(new LatLng(cur_lat , cur_long))
                            .title("marker" );
                    mMap.addMarker(markerOptions);

                    flag =1;
                    change_btn.playAnimation();
                }
                else if(flag ==1){
                    mMap.clear();
                    textView_1.setText("나와 다른");
                    for(int i=0; i< arrayList.size(); i++){

                        String tmp = arrayList.get(i).getUser_mbti();
                        int check =0;
                        for(int j=0; j<4;j++){
                            if(tmp.charAt(j) == person_mbti.charAt(j) ){
                                check++;
                                Log.d("checking!!!!", String.valueOf(check));
                            }
                        }

                        if(check <3){
                            Log.d("size ar", String.valueOf(arrayList.size()));
                            //버튼 클릭시 원하는 사용자 맵에 추가
                            MarkerOptions markerOptions = new MarkerOptions();
                            idx_lat = arrayList.get(i).getLatitude();
                            idx_long = arrayList.get(i).getLongitude();
                            markerOptions
                                    .position(new LatLng(idx_lat , idx_long))
                                    .title(arrayList.get(i).getUser_name())
                                    .snippet(arrayList.get(i).getUser_mbti())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            mMap.addMarker(markerOptions);

                            CircleOptions circle2_5KM = new CircleOptions().center(new LatLng(cur_lat , cur_long))
                                    .radius(1500)
                                    .strokeWidth(0f)
                                    .fillColor(0x22FFD500);
                            mMap.addCircle(circle2_5KM);
                        }
                    }

                    //현재위치 추가
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions
                            .position(new LatLng(cur_lat , cur_long))
                            .title("marker" );
                    mMap.addMarker(markerOptions);

                    flag =0;
                    change_btn.playAnimation();
                }
            }
        });

        cur_lat = findGeoPoint(getActivity(), ex1).getLatitude();
        cur_long = findGeoPoint(getActivity(), ex1).getLongitude();

        textView1 = rootView.findViewById(R.id.textView);
//        textView2 = rootView.findViewById(R.id.textView2);
//        textView1.setText(accountName + "2");
//        button = (Button)rootView.findViewById(R.id.button1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return rootView;
    }



    public static Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(new LatLng(cur_lat , cur_long))
                .title("marker" );
        mMap.addMarker(markerOptions);
        LatLng latLng = new LatLng(cur_lat, cur_long);
        CameraPosition position = new CameraPosition.Builder()
                .target(latLng).zoom(13f).build();
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
        //custome dialog 띄어주기
        user_name.setText(marker.getTitle());
        user_mbti.setText(marker.getSnippet());

        tmp_mbti = marker.getSnippet().toLowerCase();
        switch (tmp_mbti){
            case  "enfj":
                user_mbti_img.setImageResource(R.drawable.enfj);
                break;
            case  "enfp":
                user_mbti_img.setImageResource(R.drawable.enfp);
                break;
            case  "entj":
                user_mbti_img.setImageResource(R.drawable.entj);
                break;
            case  "entp":
                user_mbti_img.setImageResource(R.drawable.entp);
                break;
            case  "esfj":
                user_mbti_img.setImageResource(R.drawable.esfj);
                break;
            case  "esfp":
                user_mbti_img.setImageResource(R.drawable.esfp);
                break;
            case  "estj":
                user_mbti_img.setImageResource(R.drawable.estj);
                break;
            case  "estp":
                user_mbti_img.setImageResource(R.drawable.estp);
                break;
            case  "infj":
                user_mbti_img.setImageResource(R.drawable.infj);
                break;
            case  "infp":
                user_mbti_img.setImageResource(R.drawable.infp);
                break;
            case  "intj":
                user_mbti_img.setImageResource(R.drawable.intj);
                break;
            case  "intp":
                user_mbti_img.setImageResource(R.drawable.intp);
                break;
            case  "isfj":
                user_mbti_img.setImageResource(R.drawable.isfj);
                break;
            case  "isfp":
                user_mbti_img.setImageResource(R.drawable.isfp);
                break;
            case  "istj":
                user_mbti_img.setImageResource(R.drawable.istj);
                break;
            case  "istp":
                user_mbti_img.setImageResource(R.drawable.istp);
                break;
            default:
                break;
        }
        alertDialog.show();

        return true;
    }
}
