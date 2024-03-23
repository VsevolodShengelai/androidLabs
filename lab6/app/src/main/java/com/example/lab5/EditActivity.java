package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText noteName, noteDescription;
    int currentId = -1;
    String actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        noteName = findViewById(R.id.noteNameEditText);
        noteDescription = findViewById(R.id.noteDescriptionEditText);

        Bundle arguments = getIntent().getExtras();

        actionType = arguments.getString("ACTION_TYPE");
        if ("edit".equals(actionType)){
            noteName.setText(arguments.get("NOTE_NAME").toString());
            noteDescription.setText(arguments.get("NOTE_DESCRIPTION").toString());
            currentId = arguments.getInt("CURRENT_ID");
        }
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    public void saveNoteButtonClick(View view) {
        String noteNameContent = noteName.getText().toString().trim();
        String noteDescriptionContent = noteDescription.getText().toString().trim();

        if ("add".equals(actionType)) {
            if (noteNameContent.isEmpty() || noteDescriptionContent.isEmpty()) {
                showToast("Имя заметки и описание не могут быть пустыми!");
            } else {
                showToast("Заметка создана успешно!");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT_NOTE_NAME", noteName.getText().toString());
                returnIntent.putExtra("RESULT_NOTE_DESCRIPTION", noteDescription.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        } else if ("edit".equals(actionType)) {
            if (noteNameContent.isEmpty() || noteDescriptionContent.isEmpty()) {
                showToast("Имя заметки и описание не могут быть пустыми!");
            } else {
                showToast("Заметка отредактирована успешно!");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT_NOTE_NAME", noteName.getText().toString());
                returnIntent.putExtra("RESULT_NOTE_DESCRIPTION", noteDescription.getText().toString());
                returnIntent.putExtra("RESULT_CURRENT_ID", currentId);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
    }
}