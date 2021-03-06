package com.example.smartmirror;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.chromium.net.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class CategoryFragment2 extends Fragment {

    ListView categoryList;
    ListItem listItem;
    UserRecAdapter adapter;

    private String IP_ADDRESS="54.180.67.155";
    //private String IP_ADDRESS=getString(R.string.IP);

    //private String IP_ADDRESS=getString(R.string.IP);
    String TAG="php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryList = view.findViewById(R.id.categoryList);
        listItem = new ListItem();
        adapter = new UserRecAdapter(getContext());

        virtualfittingThread recommend=new virtualfittingThread();
        try {
            String result=recommend.execute("rec").get();
            Log.e("[socket result-]",result);
            String[] recommendation_case=result.split("-");
            String cases=Integer.toString(recommendation_case.length); // ???????????? ???????????? ??????
            int case_=0; // ?????????-??????-????????? 3 ??????-????????? 2
            StringBuilder parameters=new StringBuilder();
            parameters.append("?COUNT="+cases);
            for(int i=0;i<recommendation_case.length;i++)
            {
                String[] ids=recommendation_case[i].split("/");
                case_=ids.length;
                if(ids.length==2)
                {
                    parameters.append("&"+i+"TOP="+ids[0]);
                    parameters.append("&"+i+"BOTTOM="+ids[1]);

                }
                else if(ids.length==3)
                {
                    parameters.append("&"+i+"OUTER="+ids[0]);
                    parameters.append("&"+i+"TOP="+ids[1]);
                    parameters.append("&"+i+"BOTTOM="+ids[2]);
                }

            }
            parameters.append("&CASE="+case_);
            GetRecommendation networkTask=new GetRecommendation();
            networkTask.execute("http://"+IP_ADDRESS+"/getRecommendation.php",parameters.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        categoryList.setAdapter(adapter);


    }
    class GetRecommendation extends AsyncTask<String, Void,String> {

        ProgressDialog progressDialog;

        protected void onPreExecute()
        {
            super.onPreExecute();
            //progressDialog=ProgressDialog.show(CategoryFragment.this, "Please Wait",null,true,true);
        }
        protected void onPostExecute(String result) // doInBackground()????????? ????????? ?????? onPostExecute()??? ??????????????? ?????????.
        {
            super.onPostExecute(result);
            listItem = new ListItem();
            adapter = new UserRecAdapter(getContext());

            int cases=0;
            try {
                Log.e(">>result: ",result);
                JSONObject jsonObject=new JSONObject(result); // ?????? ??? JSONObject
                JSONArray jsonArray=jsonObject.getJSONArray("User_clothes_recommendation"); // ????????? ?????????
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);

                    Log.e("case",String.valueOf(object.length()));
                    if(object.length()==4)
                    {
                        String top_image=object.getString("Top").toString();
                        String top_id=object.getString("Top_ID").toString();
                        String bottom_image=object.getString("Bottom").toString();
                        String bottom_id=object.getString("Bottom_ID").toString();

                        adapter.addItem("",top_image,bottom_image,"",top_id,bottom_id);
                    }
                    else if(object.length()==6)
                    {
                        String outer_image=object.getString("Outer").toString();
                        String outer_id=object.getString("Outer_ID").toString();
                        String top_image=object.getString("Top").toString();
                        String top_id=object.getString("Top_ID").toString();
                        String bottom_image=object.getString("Bottom").toString();
                        String bottom_id=object.getString("Bottom_ID").toString();

                        adapter.addItem(outer_image,top_image,bottom_image,outer_id,top_id,bottom_id);
                    }
                }
                categoryList.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "POST response  - " + result);
        }
        @Override
        protected String doInBackground(String... params) {
            String parameters=(String)params[1];
            String serverURL = (String)params[0];
            serverURL = serverURL+parameters ;
            Log.d("url: ",String.valueOf(serverURL));
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "GET response code - " + responseStatusCode);

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
                Log.d(TAG, "GetData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
