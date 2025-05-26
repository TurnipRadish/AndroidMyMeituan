package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Map;

public class UserSelectActivity extends AppCompatActivity {
    private ListView userListView;
    private Map<String, String> userInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        userInf = (Map<String, String>) getIntent().getSerializableExtra("userInf");
        userListView = findViewById(R.id.user_list_view);

        ArrayList<String> usernames = new ArrayList<>(userInf.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        userListView.setAdapter(adapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = usernames.get(position);
                String selectedPassword = userInf.get(selectedUser);

                Intent intent = new Intent(UserSelectActivity.this, MainActivity.class);
                intent.putExtra("selectedUser", selectedUser);
                intent.putExtra("selectedPassword", selectedPassword);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}