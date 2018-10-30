package com.donguibogam.donguibogam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Drug_Search extends Activity{

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ITEM_CODE = "item_code";
    private static final String TAG_ITEM_NAME = "item_name";
    private static final String TAG_ENTP_NAME = "entp_name";
    private static final String TAG_CLASS_NO = "class_no";
    private static final String TAG_OTC = "otc";
    private static final String TAG_BAR_CODE = "bar_code";
    private static final String TAG_MATERIAL_NAME = "material_name";
    private static final String TAG_CAPACITY = "capacity";
    private static final String TAG_PRECAUTION = "precaution";
    private static final String TAG_INSERT_FILE = "insert_file";
    private static final String TAG_STORAGE_METHOD = "storage_method";
    private static final String TAG_VALID_TERM = "valid_term";
    private static final String TAG_PACK_UNIT = "pack_unit";
    private static final String TAG_CHART = "chart";

    private ArrayList<Drug_Item> data = null;

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    ArrayList<Drug_Item> itemlist = new ArrayList<Drug_Item>();


    ListView lv;
    ArrayList<String> datas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_search);

        EditText text = (EditText) findViewById(R.id.EditText);
        text.setOnKeyListener(enterkey);

        Intent intent = getIntent();
        String simplesearchname = intent.getStringExtra("simplesearchname");

        if(!(simplesearchname.equals("") || simplesearchname.charAt(0) == ' ' || simplesearchname.charAt(0) == '\n'))
        {
            Do_simpleshow(simplesearchname);
        }

        //getData("http://172.20.10.3:8080/index.php"); //수정 필요   //  IP 보고 바까야함
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(Drug_Search.this, datas.get(position), Toast.LENGTH_SHORT).show();
        }
    };

    protected void showList() {
        try {
            itemlist.clear();
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String item_code = c.getString(TAG_ITEM_CODE);
                String item_name = c.getString(TAG_ITEM_NAME);
                String entp_name = c.getString(TAG_ENTP_NAME);
                String class_no = c.getString(TAG_CLASS_NO);
                String otc = c.getString(TAG_OTC);
                String bar_code = c.getString(TAG_BAR_CODE);
                String material_name = c.getString(TAG_MATERIAL_NAME);
                String capacity = c.getString(TAG_CAPACITY);
                String precaution = c.getString(TAG_PRECAUTION);
                String insert_file = c.getString(TAG_INSERT_FILE);
                String storage_method = c.getString(TAG_STORAGE_METHOD);
                String valid_term = c.getString(TAG_VALID_TERM);
                String pack_unit = c.getString(TAG_PACK_UNIT);
                String chart = c.getString(TAG_CHART);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ITEM_CODE, item_code);
                persons.put(TAG_ITEM_NAME, item_name);
                persons.put(TAG_ENTP_NAME, entp_name);
                persons.put(TAG_CLASS_NO, class_no);
                persons.put(TAG_OTC, otc);
                persons.put(TAG_BAR_CODE, bar_code);
                persons.put(TAG_MATERIAL_NAME, material_name);
                persons.put(TAG_CAPACITY, capacity);
                persons.put(TAG_PRECAUTION, precaution);
                persons.put(TAG_INSERT_FILE, insert_file);
                persons.put(TAG_STORAGE_METHOD, storage_method);
                persons.put(TAG_VALID_TERM, valid_term);
                persons.put(TAG_PACK_UNIT, pack_unit);
                persons.put(TAG_CHART, chart);

                itemlist.add(new Drug_Item(item_code.toString(), item_name.toString(), entp_name.toString(), class_no.toString(), otc.toString(),
                        bar_code.toString(), material_name.toString(), capacity.toString(), precaution.toString(), insert_file.toString(),
                        storage_method.toString(), valid_term.toString(), pack_unit.toString(), chart.toString()));

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Drug_Search.this, personList, R.layout.item_list,
                    new String[]{TAG_ITEM_NAME, TAG_ENTP_NAME, TAG_CLASS_NO},
                    new int[]{R.id.item_name, R.id.entp_name, R.id.class_no}
            );
            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "검색 완료", Toast.LENGTH_SHORT).show();
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
                    return null;
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
                EditText e = (EditText)findViewById(R.id.EditText);
                if(e.getText().toString().equals("") || e.getText().toString().charAt(0) == ' ' || e.getText().toString().charAt(0) == '\n') {
                    e.setText("");
                    Toast.makeText(getApplicationContext(), "입력해라", Toast.LENGTH_SHORT).show(); return;}
                else Do_detailshow();
                break;
        }
    }

    private void Do_detailshow()
    {
        EditText edt = (EditText)findViewById(R.id.EditText);  // EditText 레이아웃에서 가져오기
        String text = edt.getText().toString();                 // EditText 값을 String형 변수에 저장
        edt.setText("");

        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://minsucp.iptime.org/select.php?item_name="+text); //수정 필요

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), itemlist.get(position).getEntp_name(), Toast.LENGTH_SHORT).show();
                android.content.Intent intent = new android.content.Intent(getApplicationContext(), Drug_Item_Show.class);

                intent.putExtra("item", itemlist.get(position));
                //intent.putExtra("item", itemlist.get(position).getEntp_name().toString());
                startActivity(intent);
            }
        });
    }

    public void Do_simpleshow(String simplesearchname)
    {
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://minsucp.iptime.org/select.php?item_name="+simplesearchname); //수정 필요

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), itemlist.get(position).getEntp_name(), Toast.LENGTH_SHORT).show();
                android.content.Intent intent = new android.content.Intent(getApplicationContext(), Drug_Item_Show.class);

                intent.putExtra("item", itemlist.get(position));
                //intent.putExtra("item", itemlist.get(position).getEntp_name().toString());
                startActivity(intent);
            }
        });
    }

    View.OnKeyListener enterkey = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if(keyCode == 66)
            {
                Do_detailshow();
                return true;
            }

            return false;
        }
    };

    /*
    private class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String item_name = (String)parent.getAdapter().getItem(position);
            Toast.makeText(getApplicationContext(), item_name, Toast.LENGTH_SHORT).show();

            //Intent intent = new Intent(getApplicationContext(), SearchClicked.class);
            //intent.putExtra("item_name", data.get(position).getItem_name());
            //intent.putExtra("entp_name", data.get(position).getEntp_name());
            //intent.putExtra("class_no", data.get(position).getClass_no());
            //startActivity(intent);
        }
    };
        //ListView lv = (ListView) findViewById(R.id.listView);
        //SearchAdapter adapter = new SearchAdapter(this, R.layout.drug_search2, data);
        //lv.setAdapter(adapter);

        //lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        지우기가 아까워*/
}