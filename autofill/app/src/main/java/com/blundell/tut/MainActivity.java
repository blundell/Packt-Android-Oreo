package com.blundell.tut;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // everything is
        // here https://github.com/googlesamples/android-AutofillFramework

        // autofill hints
        //(see xml)

        // Mark fields as important for autofill
        // TODO
    }

    public void onClickLogin(View view) {
        Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show();
    }
}
