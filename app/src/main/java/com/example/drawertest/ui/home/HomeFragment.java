package com.example.drawertest.ui.home;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.drawertest.MyAdapter;
import com.example.drawertest.MySingleton;
import com.example.drawertest.R;
import com.example.drawertest.SensorData;
import com.example.drawertest.databinding.FragmentHomeBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    ListView listView;
    ArrayList<SensorData> arrayOfSensorData = new ArrayList<>();
    int spinnerPosition=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fetchJson();
        final ProgressBar loading_spinner=binding.loadingSpinner;
        final LinearLayout content=binding.content;
        loading_spinner.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String dividebyTen(String str){
        double dnum = Double.parseDouble(str);
        dnum   =   dnum/10;
        DecimalFormat df = new DecimalFormat("#.#");
        df.format(dnum);
        String val    =   String.valueOf(dnum);
        return val;
    }

    public void fetchJson(){
        String url = "http://api.plottingly.com/getData.php";
        final TextView textCreatedAt = binding.textCreatedAt;
        final TextView textTemp = binding.textTemp;
        final TextView textHum = binding.textHum;
        final TextView textWind =  binding.textWind;
        final TextView textPressure = binding.textPressure;
        final ProgressBar loading_spinner=binding.loadingSpinner;
        final LinearLayout content=binding.content;

        Spinner spinner = (Spinner) binding.getRoot().findViewById(R.id.spinner);
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
                        textTemp.setText(dividebyTen(air_temp));
                        textHum.setText(dividebyTen(humidity));
                        textWind.setText(dividebyTen(wind_speed));
                        textPressure.setText(dividebyTen(pressure));

                        HashSet<String> createdAtList=new HashSet<String>();
                        for(int i=0;i<response.length();i++){
                            JSONObject jsonObject2 = response.getJSONObject(i);
                            String created_at2=jsonObject2.getString("created_at");
                            String created_at2_trim[]=created_at2.split(",",2);
                            createdAtList.add(created_at2_trim[0]);
                        }

                        ArrayList<String> createdAtArrayList=new ArrayList<>(createdAtList);
                        Collections.sort(createdAtArrayList,Collections.reverseOrder());
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                (getContext(), android.R.layout.simple_spinner_item,
                                        createdAtArrayList);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerArrayAdapter);
                        loading_spinner.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                        //help: https://camposha.info/android-examples/android-volley-spinner/
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    arrayOfSensorData.clear();
                                    for(int i=1;i<response.length();i++){
                                        JSONObject jsonObject1 = response.getJSONObject(i);
                                        if(jsonObject1.getString("created_at").split(",",2)[0].equals(spinner.getItemAtPosition(position).toString())) {
                                            String id1 = jsonObject1.getString("id");
                                            String air_temp1 = jsonObject1.getString("air_temperature");
                                            String humidity1 = jsonObject1.getString("humidity");
                                            String wind_speed1 = jsonObject1.getString("wind_speed");
                                            String pressure1 = jsonObject1.getString("barometric_pressure");
                                            String created_at1 = jsonObject1.getString("created_at");
                                            SensorData sensorData = new SensorData(id1, dividebyTen(air_temp1), dividebyTen(humidity1), dividebyTen(wind_speed1), dividebyTen(pressure1), created_at1);
                                            arrayOfSensorData.add(sensorData);
                                        }
                                    }
                                    listView = binding.listView;
                                    MyAdapter adapter = new MyAdapter(getActivity(), arrayOfSensorData);
                                    adapter.notifyDataSetChanged();
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                                            String itemPicked = "Air Temperature: "+arrayOfSensorData.get(position).getAirTemperature()+" °C\nHumidity: "+
                                                                arrayOfSensorData.get(position).getHumidity()+" %\nWind Speed: "+
                                                                arrayOfSensorData.get(position).getWindSpeed()+" m/s\nBarometric Pressure: "+
                                                                arrayOfSensorData.get(position).getBarometricPressure()+" hPa\nOn "+
                                                                arrayOfSensorData.get(position).getCreatedAt();
                                            Toast.makeText(getActivity(), itemPicked, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                catch (Exception e){

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {
                    content.setVisibility(View.GONE);
                    loading_spinner.setVisibility(View.VISIBLE);
                    Log.v("MyActivity","Something went wrong!");
                    error.printStackTrace();

                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }

}