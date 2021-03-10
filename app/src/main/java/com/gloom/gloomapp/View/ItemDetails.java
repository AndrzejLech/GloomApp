package com.gloom.gloomapp.View;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gloom.gloomapp.R;

public class ItemDetails extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);


        Button bb = findViewById(R.id.IbackButton);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ItemsActivity.class);
                startActivity(i);
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        TextView name = findViewById(R.id.IlayoutTitle);
        TextView description = findViewById(R.id.Idescription);
        TextView price = findViewById(R.id.Iprice);
        TextView vType = findViewById(R.id.Itype);
        TextView vState = findViewById(R.id.Istate);

        String type = args.getString("type");
        String state = args.getString("state");

        name.setText(args.getString("name"));
        description.setText(args.getString("description"));
        price.setText(args.getString("price"));

        switch (type) {
            case "1":
                vType.setText("Jednoręczny");
            case "2":
                vType.setText("Dwuręczny");
                break;
            case "3":
                vType.setText("Hełm");
                break;
            case "4":
                vType.setText("Tors");
                break;
            case "5":
                vType.setText("Nogi");
                break;
            case "6":
                vType.setText("Mały przedmiot");
                break;
        }

        switch (state) {
            case "1":
                vState.setText("Obracany");
                break;
            case "2":
                vState.setText("Odrzucany");
                break;
            case "3":
                vState.setText("Pasywny");
                break;
        }
    }
}