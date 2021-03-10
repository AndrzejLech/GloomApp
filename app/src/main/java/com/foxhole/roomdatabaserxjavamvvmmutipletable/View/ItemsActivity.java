package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter.ItemJSONAdapter;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Item;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ItemsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Item> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listView = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<>();

        TextView title = findViewById(R.id.ClayoutTitle);
        title.setText("Przedmioty");

        Button bb = findViewById(R.id.backButton);
        bb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PartyActivity.class);
                startActivity(i);
                finish();
            }
        });

        try {
            JSONObject object = new JSONObject(readJSON("Items.json"));
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("ItemID");
                String name = jsonObject.getString("ItemName");
                String price = jsonObject.getString("ItemPrice");
                String state = jsonObject.getString("StateID");
                String type = jsonObject.getString("TypeID");
                String description = jsonObject.getString("ItemDescription");

                Item model = new Item();
                model.setName(name);
                model.setDescription(description);
                model.setPrice(price);
                model.setType(type);
                model.setState(state);


                arrayList.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ItemJSONAdapter adapter = new ItemJSONAdapter(this, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i2 = new Intent(ItemsActivity.this, ItemDetails.class);

                Bundle args = new Bundle();
                args.putString("name", arrayList.get(position).getName());
                args.putString("description", arrayList.get(position).getDescription());
                args.putString("price", arrayList.get(position).getPrice());
                args.putString("type", arrayList.get(position).getType());
                args.putString("state", arrayList.get(position).getState());


                i2.putExtra("BUNDLE", args);
                startActivity(i2);
            }
        });
    }

    public String readJSON(String filename) {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }
}





