package com.blundell.tut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.widget.EditText;

public class GameActivity extends AppCompatActivity {

    private EditText inputUsername;
    private SurfaceView gameSurface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        AutofillManager autofillManager = getSystemService(AutofillManager.class);

        inputUsername = findViewById(R.id.editText);
        gameSurface = findViewById(R.id.surfaceView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputUsername.setVisibility(View.GONE);
                gameSurface.setVisibility(View.VISIBLE);

            }
        });

    }
}
