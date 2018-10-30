
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

public class Member_join_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_join);

        Intent intent2 = getIntent();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.join_butt_ok:          // 가입 버튼 클릭시
                EditText idText = (EditText)findViewById(R.id.join_id);      // 에디트박스에 작성한 값을 가져옴
                EditText pwText = (EditText)findViewById(R.id.join_pw);
                EditText nameText = (EditText)findViewById(R.id.join_name);
                EditText ageText = (EditText)findViewById(R.id.join_age);

                String id = idText.getText().toString();       // 가져온 값을 변수에 저장
                String pw = pwText.getText().toString();
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();

                if(id.equals("") || pw.equals("") || name.equals("") || age.equals("")){   // 가져온 값이 공백인지 검사
                    Toast.makeText(this, "빈칸없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertToDatabase(id, pw, name, age);            // 공백이 없으면 데이터베이스에 넣는 함수 실행~

                    idText.setText("");       // 작성한 에디트박스는 깔끔하게 지워주고
                    pwText.setText("");
                    nameText.setText("");
                    ageText.setText("");

                    Intent bye = getIntent();  // 화면 뒤로 갈거
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
        bye1.putExtra("isEnd","Y");          // 백키를 누르면 화면 뒤로 갈겨
        setResult(RESULT_OK,bye1);
        finish();
    }

    private void insertToDatabase(String id, String pw, String name, String age){

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
                    String USER_ID = (String)params[0];
                    String USER_PW = (String)params[1];
                    String USER_NAME = (String)params[2];
                    String USER_AGE= (String)params[3];

                    String link="http://127.0.0.1/join2.php";  //실행할 php페이지
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(USER_ID, "UTF-8");
                    data += "&" + URLEncoder.encode("pw", "UTF-8") + "=" + URLEncoder.encode(USER_PW, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEnco    der.encode(USER_NAME, "UTF-8");
                    data += "&" + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(USER_AGE, "UTF-8");

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
        task.execute(id, pw, name, age);  //쓰레드 시작

    }
}