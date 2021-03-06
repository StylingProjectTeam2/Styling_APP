package com.example.smartmirror;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ChangeUserItemProperty extends AppCompatActivity {
    String category;
    String ID;
    String top_sleeve_length,top_print,top_category;
    String bottom_category,bottom_length,bottom_fit;
    String outer_category;
    String temperature_section;
    Button save;
    String url;

    String default_top_sleeve_length,default_top_print,default_top_category;
    String default_bottom_category,default_bottom_length,default_bottom_fit;
    String default_outer_category;

    Spinner top_sleeve_length_spinner ;
    ArrayAdapter top_sleeve_length_adapter;
    Spinner top_print_spinner ;
    ArrayAdapter top_print_adapter;
    Spinner top_category_spinner ;
    ArrayAdapter top_category_adapter;

    Spinner bottom_length_spinner ;
    ArrayAdapter bottom_length_adapter ;
    Spinner bottom_fit_spinner ;
    ArrayAdapter bottom_fit_adapter;
    Spinner bottom_category_spinner ;
    ArrayAdapter bottom_category_adapter ;

    Spinner outer_category_spinner;
    ArrayAdapter outer_category_adapter;
    int state=0;
    private String IP_ADDRESS=getString(R.string.IP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        category=intent.getStringExtra("category");
        Log.e("category",category);
        ID=intent.getStringExtra("ID");
        GetData get_stored_values_task=new GetData();
        get_stored_values_task.execute("http://"+IP_ADDRESS+"/getUserItemProperty.php?CATEGORY="+category+"&ID="+ID);

        super.onCreate(savedInstanceState);





            if (category.equals("top")) {
                setContentView(R.layout.top_properties);

               top_sleeve_length_spinner = findViewById(R.id.sleeve_length);
                top_sleeve_length_adapter = ArrayAdapter.createFromResource(this, R.array.top_sleeve_length, R.layout.support_simple_spinner_dropdown_item);
                top_sleeve_length_spinner.setAdapter(top_sleeve_length_adapter);


                 top_print_spinner = findViewById(R.id.print);
                 top_print_adapter = ArrayAdapter.createFromResource(this, R.array.top_print, R.layout.support_simple_spinner_dropdown_item);
                top_print_spinner.setAdapter(top_print_adapter);


                 top_category_spinner = findViewById(R.id.category);
                 top_category_adapter = ArrayAdapter.createFromResource(this, R.array.top_category, R.layout.support_simple_spinner_dropdown_item);
                top_category_spinner.setAdapter(top_category_adapter);


                save = findViewById(R.id.btn_saveInfo);
                save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        top_sleeve_length = top_sleeve_length_spinner.getSelectedItem().toString();
                        top_print = top_print_spinner.getSelectedItem().toString();
                        top_category = top_category_spinner.getSelectedItem().toString();
                        if (top_category.equals("?????????")) {
                            temperature_section = "1";
                        } else if (top_category.equals("??????")) {
                            temperature_section = "2";
                        } else if (top_category.equals("?????????") || top_category.equals("??????") || top_category.equals("?????????")) {
                            temperature_section = "3";
                        }
                        url = "http://"+IP_ADDRESS+"/changeInfo.php?CASE=top&TOP_SLEEVE=" + top_sleeve_length + "&TOP_PRINT=" + top_print + "&TOP_CATEGORY=" + top_category + "&TEMP=" + temperature_section + "&ID=" + ID;
                        UpdateData task = new UpdateData();
                        task.execute(url);
                    }
                });
            } else if (category.equals("bottom")) {
                setContentView(R.layout.bottom_properties);

                 bottom_length_spinner = findViewById(R.id.length);
                 bottom_length_adapter = ArrayAdapter.createFromResource(this, R.array.bottom_length, R.layout.support_simple_spinner_dropdown_item);
                bottom_length_spinner.setAdapter(bottom_length_adapter);


                 bottom_fit_spinner = findViewById(R.id.fit);
                 bottom_fit_adapter = ArrayAdapter.createFromResource(this, R.array.bottom_fit, R.layout.support_simple_spinner_dropdown_item);
                bottom_fit_spinner.setAdapter(bottom_fit_adapter);


                 bottom_category_spinner = findViewById(R.id.category);
                 bottom_category_adapter = ArrayAdapter.createFromResource(this, R.array.bottom_category, R.layout.support_simple_spinner_dropdown_item);
                bottom_category_spinner.setAdapter(bottom_category_adapter);




                save = findViewById(R.id.btn_saveInfo);
                save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        bottom_length = bottom_length_spinner.getSelectedItem().toString();
                        if (bottom_length.equals("??????")) {
                            temperature_section = "1";
                        } else {
                            temperature_section = "3";
                        }
                        bottom_category = bottom_category_spinner.getSelectedItem().toString();
                        bottom_fit = bottom_fit_spinner.getSelectedItem().toString();
                        url = "http://"+IP_ADDRESS+"/changeInfo.php?CASE=bottom&BOTTOM_LENGTH=" + bottom_length + "&BOTTOM_CATEGORY=" + bottom_category + "&BOTTOM_FIT=" + bottom_fit + "&TEMP=" + temperature_section + "&ID=" + ID ;
                        UpdateData task = new UpdateData();
                        task.execute(url);
                    }
                });
            } else if (category.equals("outer")) {
                setContentView(R.layout.outer_properties);
                 outer_category_spinner = findViewById(R.id.category);
                 outer_category_adapter = ArrayAdapter.createFromResource(this, R.array.outer_category, R.layout.support_simple_spinner_dropdown_item);
                outer_category_spinner.setAdapter(outer_category_adapter);




                save = findViewById(R.id.btn_saveInfo);
                save.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        outer_category = outer_category_spinner.getSelectedItem().toString();
                        if (outer_category.equals("????????????") || outer_category.equals("???????????????")) {
                            temperature_section = "4";
                        } else if (outer_category.equals("????????????") || outer_category.equals("????????? ??????") || outer_category.equals("?????????") || outer_category.equals("?????????") || outer_category.equals("????????????")) {
                            temperature_section = "5";
                        } else if (outer_category.equals("?????????") || outer_category.equals("??????") || outer_category.equals("?????????")) {
                            temperature_section = "6";
                        } else if (outer_category.equals("?????????")) {
                            temperature_section = "7";
                        }
                        url = "http://"+IP_ADDRESS+"/changeInfo.php?CASE=outer&OUTER_CATEGORY=" + outer_category + "&TEMP=" + temperature_section + "&ID=" + ID;
                        UpdateData task = new UpdateData();
                        task.execute(url);
                    }
                });
            }


    }
    class GetData extends AsyncTask<String, Void,String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressDialog=ProgressDialog.show(CategoryFragment.this, "Please Wait",null,true,true);
        }
        protected void onPostExecute(String result) // doInBackground()????????? ????????? ?????? onPostExecute()??? ??????????????? ?????????.
        {
            super.onPostExecute(result);

            try {
                Log.e(">>result",result);
                JSONObject jsonObject=new JSONObject(result); // ?????? ??? JSONObject
                JSONArray jsonArray=jsonObject.getJSONArray("User_items_property"); // ????????? ?????????
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);
                    if(category.equals("top"))
                    {
                        default_top_category=object.getString("Category").toString();
                        default_top_sleeve_length=object.getString("Sleeve").toString();
                        default_top_print=object.getString("TopPrint").toString();

                        int pos;

                        pos = top_sleeve_length_adapter.getPosition(default_top_sleeve_length);
                        Log.e("pos", String.valueOf(pos));
                        top_sleeve_length_spinner.setSelection(pos);
                        pos = top_print_adapter.getPosition(default_top_print);
                        top_print_spinner.setSelection(pos);
                        Log.e("pos", String.valueOf(pos));
                        pos = top_category_adapter.getPosition(default_top_category);
                        top_category_spinner.setSelection(pos);
                        Log.e("pos", String.valueOf(pos));
                    }
                    else if(category.equals("bottom"))
                    {
                        default_bottom_category=object.getString("Category").toString();
                        default_bottom_length=object.getString("Length").toString();
                        default_bottom_fit=object.getString("Bottomfit").toString();

                        int pos;

                        pos = bottom_length_adapter.getPosition(default_bottom_length);
                        bottom_length_spinner.setSelection(pos);
                        pos = bottom_fit_adapter.getPosition(default_bottom_fit);
                        bottom_fit_spinner.setSelection(pos);
                        pos = bottom_category_adapter.getPosition(default_bottom_category);
                        bottom_category_spinner.setSelection(pos);
                    }
                    else if(category.equals("outer"))
                    {
                        default_outer_category=object.get("Category").toString();

                        int pos;

                        pos = outer_category_adapter.getPosition(default_outer_category);
                        outer_category_spinner.setSelection(pos);
                    }
                }
                state=1;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(String... params) {


            String serverURL = (String)params[0];
            //TODO: change serverURL
            Log.d("url: ",String.valueOf(serverURL));
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("PHP", "GET response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                //Log.e("Result - Sb: ",String.valueOf(sb));

                return sb.toString();

            } catch (Exception e) {
                Log.d("PHP", "GetData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
    class UpdateData extends AsyncTask<String, Void,String>
    {

        protected String doInBackground(String... params) {

            //String serverURL = (String)params[0];
            String url_=(String)params[0];
            Log.d("url: ",String.valueOf(url_)); //serverURL
            try {
                URL url = new URL(url_);//serverURL
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("PHP", "GET response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                Log.e("Result - Sb: ",String.valueOf(sb));

                return sb.toString();

            } catch (Exception e) {
                Log.d("PHP", "GetData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}