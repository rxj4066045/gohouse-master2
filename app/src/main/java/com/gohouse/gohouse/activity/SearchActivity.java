package com.gohouse.gohouse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gohouse.gohouse.R;

public class SearchActivity extends Activity {

    private TextView tv_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tv_cancel = findViewById(R.id.tv_cancel);

        //设置取消监听按钮
        setCancelListen();
    }

    private void setCancelListen() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
            }
        });
    }
}
