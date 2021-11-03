package com.example.drawertest.ui.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.drawertest.MyAdapter;
import com.example.drawertest.MySingleton;
import com.example.drawertest.SensorData;
import com.example.drawertest.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    ListView listView;
    ArrayList<SensorData> arrayOfSensorData = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fetchJson();
        final ProgressBar loading_spinner=binding.loadingSpinner;
        final LinearLayout content=binding.content;
        content.setVisibility(View.GONE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_spinner.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        }, 1000);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fetchJson(){
        String url = "http://api.plottingly.com/getData.php";
        final TextView textCreatedAt = binding.textCreatedAt;
        final TextView textTemp = binding.textTemp;
        final TextView textHum = binding.textHum;
        final TextView textWind =  binding.textWind;
        final TextView textPressure = binding.textPressure;

        @SuppressLint("SetTextI18n") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        String created_at = jsonObject.getString("created_at");
                        String air_temp = jsonObject.getString("air_temperature");
                        String humidity = jsonObject.getString("humidity");
                        String wind_speed = jsonObject.getString("wind_speed");
                        String pressure = jsonObject.getString("barometric_pressure");
                        textCreatedAt.setText("Updated on: "+created_at);
                        textTemp.setText(air_temp);
                        textHum.setText(humidity);
                        textWind.setText(wind_speed);
                        textPressure.setText(pressure);
                        for(int i=1;i<response.length();i++){
                            JSONObject jsonObject1 = response.getJSONObject(i);
                            String id1=jsonObject1.getString("id");
                            String air_temp1 = jsonObject1.getString("air_temperature");
                            String humidity1 = jsonObject1.getString("humidity");
                            String wind_speed1 = jsonObject1.getString("wind_speed");
                            String pressure1 = jsonObject1.getString("barometric_pressure");
                            String created_at1=jsonObject1.getString("created_at");
                            SensorData sensorData=new SensorData(id1,air_temp1,humidity1,wind_speed1,pressure1,created_at1);
                            arrayOfSensorData.add(sensorData);
                        }
                        listView = binding.listView;
                        MyAdapter adapter = new MyAdapter(getActivity(), arrayOfSensorData);
                        listView.setAdapter(adapter);
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                    textTemp.setText("Error!");
                    textHum.setText("Error!");
                    textWind.setText("Error!");
                    textPressure.setText("Error!");
                    Log.v("MyActivity","Something went wrong!");
                    error.printStackTrace();

                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}