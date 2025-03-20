package com.example.universal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universal.DataBase.RoomDB;
import com.example.universal.Models.Notes;

import java.util.List;

public class TestCreateActivity extends AppCompatActivity {
    Button btn_create;
    EditText editText_start;
    List<Notes> allWords;
    RoomDB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create);

        btn_create = findViewById(R.id.btn_create);

        editText_start = findViewById(R.id.editText_start);

        dataBase = RoomDB.getInstance(this);

        allWords = dataBase.mainDAO().getAll();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nums = editText_start.getText().toString();

                if (Integer.parseInt(nums) > allWords.size() || Integer.parseInt(nums) <= 0) {
                    Toast.makeText(TestCreateActivity.this, "Введите корректное значение", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(TestCreateActivity.this, TestActivity.class);
                    intent.putExtra("wordCount", Integer.parseInt(nums));
                    startActivity(intent);
                }

            }
        });


    }
}