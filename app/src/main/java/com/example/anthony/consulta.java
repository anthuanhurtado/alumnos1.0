package com.example.anthony;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.anthony.R;

public class consulta extends AppCompatActivity {
    Button siguiente;
    private ListView lv_alumnos_list;
    private ArrayAdapter adapter;
    private String getAllContactsURL ="https://alumnos21.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta);
        siguiente=(Button)findViewById(R.id.button);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        lv_alumnos_list = (ListView)findViewById(R.id.lv_alumnos_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_alumnos_list.setAdapter(adapter);
        webServiceRest(getAllContactsURL);

        siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent siguiente =new Intent(consulta.this,Main2Activity.class);
                startActivity(siguiente);
            }
        });
    }
    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_alumno;
        String alumno;
        String matricula;
        try{
            jsonArray = new JSONArray(jsonResult);

        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                alumno = jsonObject.getString("alumno");
                matricula = jsonObject.getString("matricula");
                adapter.add(alumno +"    "+"Matricula  "+ matricula);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

}
