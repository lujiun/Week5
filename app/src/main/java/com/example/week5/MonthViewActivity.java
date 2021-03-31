package com.example.week5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    int year;
    int month;
    int dayOfWeek;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridview);
        GridView day_of_the_week = findViewById(R.id.day_of_the_week);
        Button prevBtn = findViewById(R.id.previous_button);
        Button nextBtn = findViewById(R.id.next_button);

        final String[] dayOfTheWeek = new String[]{"일", "월", "화", "수", "목", "금", "토"};
        ArrayList<String> days = new ArrayList<String>();
        ArrayList<String> day28 = new ArrayList<String>();
        ArrayList<String> day29 = new ArrayList<String>();
        ArrayList<String> day30 = new ArrayList<String>();
        ArrayList<String> day31 = new ArrayList<String>();
        TextView yearMonthTV = findViewById(R.id.year_month);
        Calendar cal = Calendar.getInstance();

        Intent intent = getIntent();
        year = intent.getIntExtra("year", -1);
        month = intent.getIntExtra("month", -1);

        if (year == -1 || month == -1){
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
        }

        cal.set(year, month, 1);
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 첫날 요일구하기

        for(int i = 1; i <= 28; i++) day28.add("" + i);
        for(int i = 1; i <= 29; i++) day29.add("" + i);
        for(int i = 1; i <= 30; i++) day30.add("" + i);
        for(int i = 1; i <= 31; i++) day31.add("" + i); // 각각의 배열에 28~31일만큼 숫자 할당해줌

        if(dayOfWeek != 1) {
            for (int i = 1; i < dayOfWeek; i++) days.add(""); // 1일의 요일을 구해서 그 값만큼 격자 앞쪽에 빈칸 만들어줌
        }

        if (month == 1 && year % 4 != 0) days.addAll(day28); // 2월 - 28일
        if (month == 1 && year % 4 == 0) days.addAll(day29); // 2월/윤달 - 29일
        if (month == 3 || month == 5 || month == 8 || month == 10) days.addAll(day30); // 4,6,9,11월 - 30일
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) days.addAll(day31); // 1,3,5,7,8,10,12월 - 31일

        yearMonthTV.setText(year + "년 " + (month+1) + "월");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                dayOfTheWeek);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                days);

        day_of_the_week.setAdapter(adapter);
        gridView.setAdapter(adapter2);
    }
}