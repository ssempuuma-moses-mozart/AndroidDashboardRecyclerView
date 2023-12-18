package com.example.ulcerpatientapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class drugdetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugdetails);

        // Find TextViews by their IDs
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView31 = findViewById(R.id.textView31);
        TextView textView41 = findViewById(R.id.textView41);

        // Set OnClickListener for each TextView
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.squarepharma.com.bd/product-details.php?pid=792");
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.healthokaypharmacy.com/product/relcer-gel-180mls/");
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://m.indiamart.com/proddetail/antacid-syrup-22827322488.html");
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.bricklaboratories.com/product/pepcer-syrup-90ml/");
            }
        });

        textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://abiramilabs.com/product-category/syrup/antacid/");
            }
        });

        textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://dsapsherbal.com/product/git-syrup/");
            }
        });
    }

    private void openWebPage(String url) {
        // Create an Intent to open a web browser with the specified URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
