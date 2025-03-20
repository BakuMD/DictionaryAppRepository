package com.example.universal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universal.DataBase.RoomDB;
import com.example.universal.Models.Notes;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private TextView textViewWord;
    private EditText editTextTranslation;
    private Button buttonSubmit;
    private List<Notes> words;
    private int currentWordIndex = 0;
    private int correctCount = 0;
    public int wordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textViewWord = findViewById(R.id.textViewWord);
        editTextTranslation = findViewById(R.id.editTextTranslation);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        wordCount = getIntent().getIntExtra("wordCount", 0);

        RoomDB db = RoomDB.getInstance(this);

        words = db.mainDAO().getRandomWords(wordCount);

        showNextWord();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void showNextWord() {
        if (currentWordIndex < words.size()) {
            textViewWord.setText(words.get(currentWordIndex).getTitle());
            editTextTranslation.setText("");
        } else {
            finishQuiz(correctCount, wordCount);
        }
    }

    private void checkAnswer() {
        String userTranslation = editTextTranslation.getText().toString().stripTrailing();
        if (userTranslation.equalsIgnoreCase(words.get(currentWordIndex).getNotes())) {
            correctCount++;
        }
        currentWordIndex++;
        showNextWord();
    }

    private void finishQuiz(int correctAnswers, int totalWords) {
        // Создаем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_result, null);
        builder.setView(dialogView);

        // Находим TextView и Button в диалоговом окне
        TextView textResult = dialogView.findViewById(R.id.text_result);
        Button buttonClose = dialogView.findViewById(R.id.button_close);

        // Устанавливаем текст результата
        String resultText = "Правильные переводы: " + correctAnswers + "/" + totalWords;
        textResult.setText(resultText);
        if ((1 - (double) correctAnswers /totalWords) <= 0.25) {
            textResult.setTextColor(Color.GREEN);
        } else if ((1 - (double) correctAnswers /totalWords) > 0.25 && (1 - (double) correctAnswers /totalWords) < 0.75) {
            textResult.setTextColor(Color.YELLOW);
        } else {
            textResult.setTextColor(Color.RED);
        }

        // Создаем диалог
        AlertDialog dialog = builder.create();

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
}