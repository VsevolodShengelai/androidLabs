package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.models.TaskModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText noteName, noteDescription;
    Button addNoteButton, saveNoteButton, showLastNoteButton;
    ImageButton prevNote, nextNote;

    int currentId = -1;

    private ArrayList<TaskModel> notes = new ArrayList<TaskModel>() {
        {
            add(new TaskModel("Изучить работу в планировщике запросов",
                    "Прочесть соответствующий раздел в книге по Postgres"));
            add(new TaskModel("Научиться переписывать рекурсивные запросы",
                    "См. видео на Ютуб и официальную доку"));
            add(new TaskModel("Создать упражнения на рекурсивные запросы на Codewars",
                    "Их среда исполнения вообще поддерживает рекурсивные запросы?"));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        noteName = (EditText) findViewById(R.id.noteNameEditText);
        noteDescription = (EditText) findViewById(R.id.noteDescriptionEditText);
        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        saveNoteButton = (Button) findViewById(R.id.editNoteButton);
        showLastNoteButton = (Button) findViewById(R.id.showLastNoteButton);
        prevNote = (ImageButton) findViewById(R.id.prevNoteImageButton);
        nextNote = (ImageButton) findViewById(R.id.nextNoteImageButton);
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
        } else if ((currentId == notes.size()-1) || (currentId == -1)) {
            noteName.setText(notes.get(currentId).getName());
            noteDescription.setText(notes.get(currentId).getDescription());
        }
        else {
            showToast("Текущая заметка - последняя");
        }
    }

    public void showLastNoteButtonClick(View view) {
        int lastId = TaskModel.getLastId();
        currentId = lastId;

        noteName.setText(notes.get(lastId).getName());
        noteDescription.setText(notes.get(lastId).getDescription());
    }

    public void addButtonClick(View view) {
        String noteNameContent = noteName.getText().toString().trim();
        String noteDescriptionContent = noteDescription.getText().toString().trim();

        if (noteNameContent.isEmpty() || noteDescriptionContent.isEmpty()) {
            showToast("Имя заметки и описание не могут быть пустыми!");
        }
        else{
            notes.add(new TaskModel(noteNameContent, noteDescriptionContent));
            showToast("Заметка добавлена успешно!");
        }

        noteName.setText("");
        noteDescription.setText("");

        currentId = notes.size() - 1;
    }

    public void editButtonClick(View view) {
        String noteNameContent = noteName.getText().toString().trim();
        String noteDescriptionContent = noteDescription.getText().toString().trim();

        // Проверяем, выбрана ли заметка для редактирования
        if (currentId >= 0 && currentId < notes.size()) {
            // Проверяем, не пустые ли поля ввода
            if (noteNameContent.isEmpty() || noteDescriptionContent.isEmpty()) {
                showToast("Имя заметки и описание не могут быть пустыми!");
            } else {
                // Обновляем имя и описание текущей заметки
                TaskModel task = notes.get(currentId);
                task.setName(noteNameContent);
                task.setDescription(noteDescriptionContent);
                showToast("Заметка обновлена успешно!");
            }
        } else {
            showToast("Пожалуйста, выберите заметку для редактирования!");
        }
    }

}