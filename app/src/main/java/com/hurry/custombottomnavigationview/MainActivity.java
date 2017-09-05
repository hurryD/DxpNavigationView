package com.hurry.custombottomnavigationview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hurry.dxpnavigationview.CustomBottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "mainActivity";
    private TextView mTextMessage;
    private String[] ngTexts = {"首页","定制","消息","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        //Activity
        CustomBottomNavigationView ngView = (CustomBottomNavigationView) findViewById(R.id.ngView_main);
        //设置导航栏点击监听
        ngView.setOnMenuClickListener(new CustomBottomNavigationView.OnMenuClickListener() {
            @Override
            public void onMenuClick(int position) {
                mTextMessage.setText(ngTexts[position]);
            }
        });
        //设置默认被选中的模块 0,1,2,3.. (可选)
        ngView.setCurrentIndex(0);
    }
}
