package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5.models.TaskModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView noteName, noteDescription;
    Button addNoteButton, saveNoteButton, showLastNoteButton;
    ImageButton prevNote, nextNote;

    int currentId = -1;

    public static final int EDIT_NOTE_REQUEST = 1; // Код запроса

    final String TAG = "MyLifecycleLog";

    private ArrayList<TaskModel> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() MainActivity вызван, savedInstance=" + savedInstanceState);

        noteName = findViewById(R.id.noteNameEditText);
        noteDescription = findViewById(R.id.noteDescriptionEditText);
        addNoteButton = findViewById(R.id.addNoteButton);
        saveNoteButton = findViewById(R.id.editNoteButton);
        showLastNoteButton = findViewById(R.id.showLastNoteButton);
        prevNote = findViewById(R.id.prevNoteImageButton);
        nextNote = findViewById(R.id.nextNoteImageButton);

        if (savedInstanceState != null) {
            MyApp app = (MyApp) getApplicationContext();
            notes = app.getNotes();

            noteName.setText(savedInstanceState.getString("NOTE_NAME"));
            noteDescription.setText(savedInstanceState.getString("NOTE_DESCRIPTION"));
            currentId = savedInstanceState.getInt("CURRENT_ID");
        } else {
            notes.add(new TaskModel("Изучить работу в планировщике запросов",
                    "Прочесть соответствующий раздел в книге по Postgres"));
            notes.add(new TaskModel("Научиться переписывать рекурсивные запросы",
                    "См. видео на Ютуб и официальную доку"));
            notes.add(new TaskModel("Создать упражнения на рекурсивные запросы на Codewars",
                    "Их среда исполнения вообще поддерживает рекурсивные запросы?"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() MainActivity вызван");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() MainActivity вызван");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() MainActivity вызван");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() MainActivity вызван");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() MainActivity вызван");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() MainActivity вызван");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState MainActivity вызван");

        MyApp app = (MyApp) getApplicationContext();
        app.setNotes(notes);

        outState.putString("NOTE_NAME", noteName.getText().toString());
        outState.putString("NOTE_DESCRIPTION", noteDescription.getText().toString());
        outState.putInt("CURRENT_ID", currentId);
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text,
                Toast.LENGTH_SHORT).show();
    }

    public void previousNoteButtonClick(View view) {
        if (currentId - 1 >= 0) {
            currentId -= 1;
            noteName.setText(notes.get(currentId).getName());
            noteDescription.setText(notes.get(currentId).getDescription());
        } else {
            showToast("Текущая заметка - первая");
        }
    }

    public void nextNoteButtonClick(View view) {
        if (currentId + 1 < notes.size()) {
            currentId += 1;
            noteName.setText(notes.get(currentId).getName());
            noteDescription.setText(notes.get(currentId).getDescription());
        } else {
            showToast("Текущая заметка - последняя");
        }
    }

    public void showLastNoteButtonClick(View view) {
        currentId = notes.size() - 1;

        noteName.setText(notes.get(currentId).getName());
        noteDescription.setText(notes.get(currentId).getDescription());
    }

    public void addButtonClick(View view) {
        String noteNameContent = noteName.getText().toString().trim();
        String noteDescriptionContent = noteDescription.getText().toString().trim();

        if (noteNameContent.isEmpty() || noteDescriptionContent.isEmpty()) {
            showToast("Имя заметки и описание не могут быть пустыми!");
        } else {
            notes.add(new TaskModel(noteNameContent, noteDescriptionContent));
            showToast("Заметка добавлена успешно!");
        }

        noteName.setText("");
        noteDescription.setText("");

        currentId = notes.size() - 1;
    }

    public void editButtonClick(View view) {
        if (currentId >= 0 && currentId < notes.size()) {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("NOTE_NAME", noteName.getText().toString());
            intent.putExtra("NOTE_DESCRIPTION", noteDescription.getText().toString());
            intent.putExtra("CURRENT_ID", currentId);
            startActivityForResult(intent, EDIT_NOTE_REQUEST);
        } else {
            showToast("Пожалуйста, выберите заметку для редактирования!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra("RESULT_NOTE_NAME");
                String description = data.getStringExtra("RESULT_NOTE_DESCRIPTION");

                noteName.setText(name);
                noteDescription.setText(description);
                currentId = data.getIntExtra("RESULT_CURRENT_ID", -1);

                TaskModel updatedTask = notes.get(currentId);
                updatedTask.setName(name);
                updatedTask.setDescription(description);
            }
        }
    }
}