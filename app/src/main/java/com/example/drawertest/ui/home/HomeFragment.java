package com.example.drawertest.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.drawertest.MyAdapter;
import com.example.drawertest.MySingleton;
import com.example.drawertest.R;
import com.example.drawertest.SensorData;
import com.example.drawertest.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Context context;
    ListView listView;
    ArrayList<SensorData> arrayOfSensorData = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void fetchJson(){
        String url = "http://api.plottingly.com/getData.php";
        final TextView textTemp = (TextView) getView().findViewById(R.id.textTemp);
        final TextView textHum = (TextView) getView().findViewById(R.id.textHum);
        final TextView textWind = (TextView) getView().findViewById(R.id.textWind);
        final TextView textPressure = (TextView) getView().findViewById(R.id.textPressure);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String air_temp = jsonObject.getString("air_temperature");
                            String humidity = jsonObject.getString("humidity");
                            String wind_speed = jsonObject.getString("wind_speed");
                            String pressure = jsonObject.getString("barometric_pressure");
                            String created_at=jsonObject.getString("created_at");
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
                            listView = (ListView) getView().findViewById(R.id.listView);
                            MyAdapter adapter = new MyAdapter(context, arrayOfSensorData);
                            listView.setAdapter(adapter);
                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textTemp.setText("Error!");
                        textHum.setText("Error!");
                        textWind.setText("Error!");
                        textPressure.setText("Error!");
                        Log.v("MyActivity","Something went wrong!");
                        error.printStackTrace();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
}