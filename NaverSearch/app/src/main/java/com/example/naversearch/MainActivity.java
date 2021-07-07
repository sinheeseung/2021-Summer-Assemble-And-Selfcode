package com.example.naversearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText EditText1;
    private Spinner ComboBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComboBox();

        initEditBox();

        initButton();
    }
    private void initEditBox() {
        EditText1 = (EditText) findViewById(R.id.EditBox1);
    }
    private void initComboBox() {
        ComboBox = (Spinner) findViewById(R.id.ComboBoxItem1);
        ArrayList<String> itemList = new ArrayList<>(4);
        itemList.add("web");
        itemList.add("blog");
        itemList.add("cafe");
        itemList.add("book");
        ArrayAdapter ComboBoxArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, itemList);
        ComboBox.setAdapter(ComboBoxArrayAdapter);

    }
    private void initButton() {
        /* 검색버튼 생성*/
        Button Button_Search = (Button) findViewById(R.id.Button_Search);
        Button_Search.setOnClickListener(mOnClickListener);
        Button_Search.setText("검색");
        /* 끝내기버튼 생성 */
        Button Button_Exit = (Button) findViewById(R.id.Button_Exit);
        Button_Exit.setOnClickListener(mOnClickListener);
        Button_Exit.setText("끝내기");
        /* 이전 페이지 버튼 생성*/
        Button Button_Prev = (Button) findViewById(R.id.Button_Prev);
        Button_Prev.setOnClickListener(mOnClickListener);
        Button_Prev.setText("이전 페이지");
        /* 다음 페이지 버튼 생성*/
        Button Button_Next = (Button) findViewById(R.id.Button_Next);
        Button_Next.setOnClickListener(mOnClickListener);
        Button_Next.setText("다음 페이지");
    }
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Button_Search:
                    //검색버튼 클릭 => 검색어와 검색유형 읽어옴
                    String type = ComboBox.getSelectedItem().toString();
                    String query = EditText1.getText().toString();

                case R.id.Button_Exit:
                    finish();
                    break;
            }
        }
    };

}