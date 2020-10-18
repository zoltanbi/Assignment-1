package ca.bcit.asn1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public final static String TAGHELPER = "keyword";

    @Override
    public void onClick(View view){
        Button search = findViewById(R.id.searchButton);
        EditText searchBar = findViewById(R.id.searchBar);
        String keyword = searchBar.getText().toString();
        if ((view == search && !keyword.isEmpty())){
            Intent i = new Intent(this, GetNews.class);
            i.putExtra(TAGHELPER, keyword);
            startActivity(i);
        } else {
            Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.searchButton);
        button.setOnClickListener(this);
    }
}