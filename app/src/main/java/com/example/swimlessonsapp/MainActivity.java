package com.example.swimlessonsapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swimlessonsapp.LessonDAO;
import com.example.swimlessonsapp.LessonListActivity;
import com.example.swimlessonsapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etChild, etCoach;
    private TextView tvDateTime;
    private Button btnSave;
    private Calendar selectedDateTime;

    private String selectedDate = "";
    private String selectedTime = "";

    private LessonDAO lessonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etChild = findViewById(R.id.etChild);
        etCoach = findViewById(R.id.etCoach);
        tvDateTime = findViewById(R.id.tvDateTime);
        btnSave = findViewById(R.id.btnSave);

        selectedDateTime = Calendar.getInstance();

        lessonDAO = new LessonDAO(this);
        lessonDAO.open();

        findViewById(R.id.btnPickDate).setOnClickListener(v -> showDatePicker());
        findViewById(R.id.btnPickTime).setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> {
            String child = etChild.getText().toString();
            String coach = etCoach.getText().toString();

            if (!selectedDate.isEmpty() && !selectedTime.isEmpty() && !child.isEmpty() && !coach.isEmpty()) {
                lessonDAO.addLesson(selectedDate, selectedTime, coach, child);
                Toast.makeText(MainActivity.this, "Занятие добавлено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    findViewById(R.id.btnViewLessons).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LessonListActivity.class);
            startActivity(intent);
        }
    });

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
        if (!selectedDate.isEmpty() && !selectedTime.isEmpty()) {
            tvDateTime.setText("Дата: " + selectedDate + ", Время: " + selectedTime);
        }
    }

    @Override
    protected void onDestroy() {
        lessonDAO.close();
        super.onDestroy();
    }
}