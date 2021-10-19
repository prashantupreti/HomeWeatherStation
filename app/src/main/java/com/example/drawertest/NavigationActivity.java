package com.example.drawertest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.drawertest.databinding.ActivityNavigationBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationBinding binding;
    Context context=this;
    ListView listView;
    ArrayList<SensorData> arrayOfSensorData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavigation.toolbar);
//        binding.appBarNavigation.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_how, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        //fetchJson();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.navigation, menu);
//        //return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void fetchJson(){
        String url = "http://api.plottingly.com/getData.php";
        final TextView textTemp = (TextView) findViewById(R.id.textTemp);
        final TextView textHum = (TextView) findViewById(R.id.textHum);
        final TextView textWind = (TextView) findViewById(R.id.textWind);
        final TextView textPressure = (TextView) findViewById(R.id.textPressure);

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
                            listView = (ListView) findViewById(R.id.listView);
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
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}