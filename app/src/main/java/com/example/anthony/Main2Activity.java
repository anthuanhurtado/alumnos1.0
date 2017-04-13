package com.example.anthony;

import android.app.AlertDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.anthony.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    String getContactURL = "https://alumnos21.herokuapp.com";
    Button btn_search;
    EditText et_id_alumno;
    TextView tv_alumno, tv_matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_id_alumno = (EditText)findViewById(R.id.et_id_alumno);
        tv_alumno = (TextView)findViewById(R.id.tv_alumno);
        tv_matricula = (TextView)findViewById(R.id.tv_matricula);

        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View s) {
        if(s == btn_search)
            searchStudent();
    }

    public void searchStudent() {
        String id_alumno = et_id_alumno.getText().toString();

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("id_alumno", id_alumno);
        String queryParams = builder.build().getEncodedQuery();

        performPostCall(getContactURL, queryParams);
    }

    public void performPostCall(String requestURL, String query){
        URL url;
        String webServiceResult="";
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    webServiceResult += line;
                }
                bufferedReader.close();
            }else {
                webServiceResult="";
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Search Activity",e.getMessage());
        }
        if(webServiceResult!=null)
            parseInformation(webServiceResult);
        else
            message("Search","Student not found");
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
                id_alumno = jsonObject.getString("id_alumno");
                alumno = jsonObject.getString("alumno");
                matricula= jsonObject.getString("matricula");

                tv_alumno.setText(alumno);
                tv_matricula.setText(matricula);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private void message(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    private void Message(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }
}