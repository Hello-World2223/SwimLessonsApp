package com.example.swimlessonsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditLessonActivity extends AppCompatActivity {

    private EditText etChild, etCoach;
    private TextView tvDateTime;
    private Button btnSave, btnPickDate, btnPickTime;
    private Calendar selectedDateTime = Calendar.getInstance();

    private long lessonId;
    private LessonDAO lessonDAO;

    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Можно переиспользовать layout из MainActivity

        // Но лучше создать отдельный XML, например activity_edit_lesson.xml
        // Ниже я покажу как его сделать

        setContentView(R.layout.activity_edit_lesson); // см. ниже

        etChild = findViewById(R.id.etChild);
        etCoach = findViewById(R.id.etCoach);
        tvDateTime = findViewById(R.id.tvDateTime);
        btnSave = findViewById(R.id.btnSave);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);

        lessonId = getIntent().getLongExtra("LESSON_ID", -1);
        lessonDAO = new LessonDAO(this);
        lessonDAO.open();

        if (lessonId != -1) {
            Lesson lesson = lessonDAO.getLessonById(lessonId);
            if (lesson != null) {
                etChild.setText(lesson.getChild());
                etCoach.setText(lesson.getCoach());
                selectedDate = lesson.getDate();
                selectedTime = lesson.getTime();
                updateDateTimeDisplay();
            }
        }

        btnPickDate.setOnClickListener(v -> showDatePicker());
        btnPickTime.setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> {
            String child = etChild.getText().toString();
            String coach = etCoach.getText().toString();

            if (!selectedDate.isEmpty() && !selectedTime.isEmpty() && !child.isEmpty() && !coach.isEmpty()) {
                lessonDAO.updateLesson(lessonId, selectedDate, selectedTime, coach, child);
                Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            selectedDateTime.set(Calendar.YEAR, year);
            selectedDateTime.set(Calendar.MONTH, monthOfYear);
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selectedDateTime.getTime());
            updateDateTimeDisplay();
        }, selectedDateTime.get(Calendar.YEAR), selectedDateTime.get(Calendar.MONTH), selectedDateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedDateTime.set(Calendar.MINUTE, minute);
            selectedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedDateTime.getTime());
            updateDateTimeDisplay();
        }, selectedDateTime.get(Calendar.HOUR_OF_DAY), selectedDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void updateDateTimeDisplay() {
        tvDateTime.setText("Дата: " + selectedDate + ", Время: " + selectedTime);
    }

    @Override
    protected void onDestroy() {
        lessonDAO.close();
        super.onDestroy();
    }
}
/*import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditLessonActivity extends AppCompatActivity {

    private EditText etChild, etCoach;
    private TextView tvDateTime;
    private Button btnSave, btnPickDate, btnPickTime;
    private Calendar selectedDateTime = Calendar.getInstance();

    private long lessonId;
    private LessonDAO lessonDAO;

    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Можно переиспользовать layout из MainActivity

        // Но лучше создать отдельный XML, например activity_edit_lesson.xml
        // Ниже я покажу как его сделать

        setContentView(R.layout.activity_edit_lesson); // см. ниже

        etChild = findViewById(R.id.etChild);
        etCoach = findViewById(R.id.etCoach);
        tvDateTime = findViewById(R.id.tvDateTime);
        btnSave = findViewById(R.id.btnSave);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnPickTime = findViewById(R.id.btnPickTime);

        lessonId = getIntent().getLongExtra("LESSON_ID", -1);
        lessonDAO = new LessonDAO(this);
        lessonDAO.open();

        if (lessonId != -1) {
            Lesson lesson = lessonDAO.getLessonById(lessonId);
            if (lesson != null) {
                etChild.setText(lesson.getChild());
                etCoach.setText(lesson.getCoach());
                selectedDate = lesson.getDate();
                selectedTime = lesson.getTime();
                updateDateTimeDisplay();
            }
        }

        btnPickDate.setOnClickListener(v -> showDatePicker());
        btnPickTime.setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> {
            String child = etChild.getText().toString();
            String coach = etCoach.getText().toString();

            if (!selectedDate.isEmpty() && !selectedTime.isEmpty() && !child.isEmpty() && !coach.isEmpty()) {
                lessonDAO.updateLesson(lessonId, selectedDate, selectedTime, coach, child);
                Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            selectedDateTime.set(Calendar.YEAR, year);
            selectedDateTime.set(Calendar.MONTH, monthOfYear);
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(selectedDateTime.getTime());
            updateDateTimeDisplay();
        }, selectedDateTime.get(Calendar.YEAR), selectedDateTime.get(Calendar.MONTH), selectedDateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedDateTime.set(Calendar.MINUTE, minute);
            selectedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedDateTime.getTime());
            updateDateTimeDisplay();
        }, selectedDateTime.get(Calendar.HOUR_OF_DAY), selectedDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void updateDateTimeDisplay() {
        tvDateTime.setText("Дата: " + selectedDate + ", Время: " + selectedTime);
    }

    @Override
    protected void onDestroy() {
        lessonDAO.close();
        super.onDestroy();
    }
}*/