package com.donguibogam.donguibogam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doSimpleSearch(View view)
    {
        EditText e = (EditText)findViewById(R.id.edit_simplesearch);
        if(e.getText().toString().equals("") || e.getText().toString().charAt(0) == ' ' || e.getText().toString().charAt(0) == '\n')
        {
            e.setText("");
            Toast.makeText(getApplicationContext(), "입력해라", Toast.LENGTH_SHORT).show(); return;
        }
        else
        {
            android.content.Intent intent = new android.content.Intent(getApplicationContext(), Drug_Search.class);
            intent.putExtra("simplesearchname", e.getText().toString());
            e.setText("");
            startActivity(intent);
        }
    }

    public void doDetailSearch(View view)
    {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), Drug_Detail_Search.class);
        startActivity(intent);
    }

    public void doMyDrugs(View view)
    {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), MyDrugs.class);
        startActivity(intent);
    }

    public void doFavorites(View view)
    {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), Favorites.class);
        startActivity(intent);
    }

    public void doDrugStore(View view)
    {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), DrugStore.class);
        startActivity(intent);
    }
    public void doJoin(View view)
    {
        android.content.Intent intent = new android.content.Intent(getApplicationContext(), Member_join_Activity.class);
        startActivity(intent);
    }
}
