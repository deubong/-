package com.example.bong.dbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butt3:
                EditText idText = (EditText)findViewById(R.id.editText3);
                EditText pwText = (EditText)findViewById(R.id.editText4);

                String id = idText.getText().toString();
                String pwd = pwText.getText().toString();

                Toast.makeText(getApplicationContext(),id+"//"+pwd,Toast.LENGTH_SHORT).show();  //성공여부 알림메시지
                insertToDatabase(idText.getText().toString(), pwText.getText().toString());
                break;

            case R.id.butt4:

                Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_SHORT).show();

                // 액티비티 전환 코드
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.butt5:

                Toast.makeText(getApplicationContext(), "액티비티 전환", Toast.LENGTH_SHORT).show();

                // 액티비티 전환 코드
                Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent2);
                break;



        }
    }

    private void insertToDatabase(String id, String pwd){

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
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();  //성공여부 알림메시지
            }

            @Override
            protected String doInBackground(String... params) {  //처리하고 싶은 내용을 작성

                try{
                    String id = (String)params[0];
                    String pwd= (String)params[1];

                    String link="http://172.17.5.140/insert.php";  //실행할 php페이지
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");

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
        task.execute(id,pwd);  //쓰레드 시작

    }
}
