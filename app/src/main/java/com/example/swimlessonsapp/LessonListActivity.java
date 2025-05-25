package com.example.swimlessonsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LessonListActivity extends AppCompatActivity {

    private ListView listViewLessons;
    private LessonDAO lessonDAO;
    private List<Lesson> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        listViewLessons = findViewById(R.id.listViewLessons);
        lessonDAO = new LessonDAO(this);
        lessonDAO.open();

        loadLessons();

        // Переход к редактированию при клике
        listViewLessons.setOnItemClickListener((parent, view, position, id) -> {
            Lesson selectedLesson = lessons.get(position);
            Intent intent = new Intent(LessonListActivity.this, EditLessonActivity.class);
            intent.putExtra("LESSON_ID", selectedLesson.getId());
            startActivity(intent);
        });

        // Удаление при долгом нажатии
        listViewLessons.setOnItemLongClickListener((parent, view, position, id) -> {
            Lesson lesson = lessons.get(position);
            lessonDAO.deleteLesson(lesson.getId());
            loadLessons();
            return true;
        });
    }

    private void loadLessons() {
        lessons = lessonDAO.getAllLessons();
        ArrayAdapter<Lesson> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lessons);
        listViewLessons.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLessons();
    }

    @Override
    protected void onDestroy() {
        lessonDAO.close();
        super.onDestroy();
    }
}