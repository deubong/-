package com.donguibogam.donguibogam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ReviewWriteActivity extends AppCompatActivity {
    String item_code;
    String item_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_review_write);

        Intent intent2 = getIntent();
        item_code = intent2.getStringExtra("item_code");
        item_name = intent2.getStringExtra("item_name");

        TextView t = (TextView) findViewById(R.id.review_drug_name);
        t.setText(item_name);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reviewcreatebutt:
                EditText userText = (EditText)findViewById(R.id.reviewuser);
                EditText sickText = (EditText)findViewById(R.id.reviewsick);
                EditText goodText = (EditText)findViewById(R.id.reviewgood);
                EditText badText = (EditText)findViewById(R.id.reviewbad);
                RatingBar ratingStar = (RatingBar)findViewById(R.id.ratingStar);

                String user = userText.getText().toString();
                String sick = sickText.getText().toString();
                String good = goodText.getText().toString();
                String bad = badText.getText().toString();
                String star = String.valueOf(ratingStar.getRating());

                if(user.equals("") || sick.equals("") || good.equals("") || bad.equals("") || star.equals("0.0")){
                    Toast.makeText(this, "빈칸없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertToDatabase(item_code, user, sick, good, bad, star);

                    userText.setText("");
                    sickText.setText("");
                    goodText.setText("");
                    badText.setText("");
                    ratingStar.setRating(0);


                    Intent bye = getIntent();
                    bye.putExtra("isEnd","Y");
                    setResult(RESULT_OK,bye);
                    finish();

                }
                break;
    }
    }
    public void onBackPressed() { // if child acticity end
        super.onBackPressed();
        Intent bye1 = getIntent();
        bye1.putExtra("isEnd","Y");
        setResult(RESULT_OK,bye1);
        finish();
    }

    private void insertToDatabase(String title,String user,String sick, String good, String bad, String star){

        class InsertData extends AsyncTask<String, Void, String>{
            //AsyncTask: 백그라운드 스레드에서 실행되는 비동기 클래스

            @Override
            protected void onPreExecute() {  //doInBackground 메소드가 실행되기 전에 실행되는 메소드
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                //doInBackground 메소드 후에 실행되는 메소드, 백그라운드 메소드의 반환값을 인자로 받아 그 결과를 화면에 반영
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {  //처리하고 싶은 내용을 작성

                try{
                    String ITEM_CODE = (String)params[0];
                    String USER_ID = (String)params[1];
                    String USER_SICK = (String)params[2];
                    String ITEM_GOOD= (String)params[3];
                    String ITEM_BAD= (String)params[4];
                    String ITEM_STAR= (String)params[5];

                    String link="http://minsucp.iptime.org/ReviewInsert.php";  //실행할 php페이지
                    String data  = URLEncoder.encode("ITEM_CODE", "UTF-8") + "=" + URLEncoder.encode(ITEM_CODE, "UTF-8");
                    data += "&" + URLEncoder.encode("USER_ID", "UTF-8") + "=" + URLEncoder.encode(USER_ID, "UTF-8");
                    data += "&" + URLEncoder.encode("USER_SICK", "UTF-8") + "=" + URLEncoder.encode(USER_SICK, "UTF-8");
                    data += "&" + URLEncoder.encode("ITEM_GOOD", "UTF-8") + "=" + URLEncoder.encode(ITEM_GOOD, "UTF-8");
                    data += "&" + URLEncoder.encode("ITEM_BAD", "UTF-8") + "=" + URLEncoder.encode(ITEM_BAD, "UTF-8");
                    data += "&" + URLEncoder.encode("ITEM_STAR", "UTF-8") + "=" + URLEncoder.encode(ITEM_STAR, "UTF-8");

                    URL url = new URL(link);  //페이지에 연결
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);  //전송허용
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder(); 
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();  //쓰레드 생성
        task.execute(item_code,user,sick,good,bad,star);  //쓰레드 시작

    }
}