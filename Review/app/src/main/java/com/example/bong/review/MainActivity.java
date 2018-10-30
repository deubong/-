package com.example.bong.review;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ITEM_CODE = "item_code";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_SICK = "user_sick";
    private static final String TAG_ITEM_GOOD = "item_good";
    private static final String TAG_ITEM_BAD = "item_bad";
    private static final String TAG_ITEM_STAR = "item_star";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String item_code = c.getString(TAG_ITEM_CODE);
                String user_id = c.getString(TAG_USER_ID);
                String user_sick = c.getString(TAG_USER_SICK);
                String item_good = c.getString(TAG_ITEM_GOOD);
                String item_bad = c.getString(TAG_ITEM_BAD);
                String item_star = c.getString(TAG_ITEM_STAR);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ITEM_CODE, item_code);
                persons.put(TAG_USER_ID, user_id);
                persons.put(TAG_USER_SICK, user_sick);
                persons.put(TAG_ITEM_GOOD, item_good);
                persons.put(TAG_ITEM_BAD, item_bad);
                persons.put(TAG_ITEM_STAR, item_star);


                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ITEM_CODE, TAG_USER_ID, TAG_USER_SICK, TAG_ITEM_GOOD, TAG_ITEM_BAD, TAG_ITEM_STAR},
                    new int[]{R.id.item_code, R.id.user_id, R.id.user_sick, R.id.item_good, R.id.item_bad, R.id.item_star}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butt1:        // 검색 버튼 클릭시

                EditText edt = (EditText)findViewById(R.id.EditText);  // EditText 레이아웃에서 가져오기
                String text = edt.getText().toString();                 // EditText 값을 String형 변수에 저장
                Toast.makeText(getApplication(), text+" 검색..",Toast.LENGTH_SHORT).show(); // 토스트 띄우기

                list = (ListView) findViewById(R.id.listView);
                personList = new ArrayList<HashMap<String, String>>();
                getData("http://minsucp.iptime.org/ReviewSelect.php?id="+text); //수정 필요
                break;

            case R.id.butt2:
/*
                        Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_SHORT).show();

                        // 액티비티 전환 코드
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent);
                break;
  */
                Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_SHORT).show();

                // 액티비티 전환 코드
                Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
