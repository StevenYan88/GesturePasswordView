package com.steven.gesturepasswordview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GesturePasswordView mGesturePasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGesturePasswordView = findViewById(R.id.gestureView);
        mGesturePasswordView.setGesturePasswordViewListener(new GesturePasswordViewListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "解锁成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "至少四个点", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "解锁失败", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
