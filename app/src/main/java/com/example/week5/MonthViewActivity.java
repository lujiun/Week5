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

import java.time.Month;
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
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1; // 첫날 요일구하기, 0부터 시작하기 위해 1을 빼주었다.

        for (int i = 0; i < dayOfWeek; i++) days.add(""); // 1일의 요일을 구해서 그 값만큼 격자 앞쪽에 빈칸 만들어줌


        if (month == 1 && year % 4 != 0) for(int i = 1; i <= 28; i++) days.add("" + i); // 2월 - 28일
        if (month == 1 && year % 4 == 0) for(int i = 1; i <= 29; i++) days.add("" + i); // 2월/윤달 - 29일
        if (month == 3 || month == 5 || month == 8 || month == 10) for(int i = 1; i <= 30; i++) days.add("" + i); // 4,6,9,11월 - 30일
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) for(int i = 1; i <= 31; i++) days.add("" + i); // 1,3,5,7,8,10,12월 - 31일

        yearMonthTV.setText(year + "년 " + (month+1) + "월");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.day_of_the_week,
                dayOfTheWeek);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.day,
                days);

        day_of_the_week.setAdapter(adapter);
        gridView.setAdapter(adapter2);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position>=dayOfWeek) { // 1의 요일보다 앞에 있을 때에는 클릭에 반응하지 않음
                    Toast.makeText(MonthViewActivity.this,
                            year + "." + (month + 1) + "." + (position - dayOfWeek + 1),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt_Intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month<1) {
                    bt_Intent.putExtra("year", year-1);
                    bt_Intent.putExtra("month", 11);
                }
                else {
                    bt_Intent.putExtra("year", year);
                    bt_Intent.putExtra("month", (month-1));
                }
                startActivity(bt_Intent);
                finish();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt_Intent = new Intent(getApplicationContext(), MonthViewActivity.class);
                if(month>10) {
                    bt_Intent.putExtra("year", year+1);
                    bt_Intent.putExtra("month", 0);
                }
                else {
                    bt_Intent.putExtra("year", year);
                    bt_Intent.putExtra("month", (month+1));
                }
                startActivity(bt_Intent);
                finish();
            }
        });
    }
}