package com.example.naversearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText EditText1;
    private Spinner ComboBox;
    private Thread mThread;
    private String inputLine;
    private ArrayList<String> titleList = new ArrayList<>();
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
                    SearchNews(type,query);
                    parsing();
                case R.id.Button_Exit:
                    finish();
                    break;
            }
        }
    };
    private void SearchNews(final String _category, final String searchWord) {
        mThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String clientId = ""; //애플리케이션 클라이언트 아이디값"
                        String clientSecret = ""; //애플리케이션 클라이언트 시크릿값"
                        try {
                            String text = URLEncoder.encode(searchWord, "UTF-8");
                            String apiURL = "https://openapi.naver.com/v1/search/" + _category + "?query=" + text; // json 결과
                            URL url = new URL(apiURL);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            con.setRequestProperty("X-Naver-Client-Id", clientId);
                            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                            int responseCode = con.getResponseCode();
                            BufferedReader br;
                            if (responseCode == 200) { // 정상 호출
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                            } else {  // 에러 발생
                                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                            }
                            inputLine = br.readLine();
                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        mThread.start();
    }

    /*검색 결과 전처리*/
    private void parsing() {
        String title;
        String link;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(inputLine);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                title = item.getString("title");
                link = item.getString("link");
                title = title.replace("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replace("&quot;", "")
                        .replace("&lt;", "").replace("&gt;", "").replace("&amp;", "").replace("=", "");
                System.out.println(title);
                titleList.add((title));;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}