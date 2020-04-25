package com.jenjenfuture.bluejackkos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button login ;
    private TextView tvSignUp ;
    private long backPressed;
    private Toast toastBack;

    private DBUser dbUser;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);
        tvSignUp = findViewById(R.id.signuptxt);

        dbUser = DBUser.getInstance(this);

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        if (sharedPreferences.getBoolean("logged",false)){
            moveActivity();
        }

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Register.class);
            startActivity(intent);
        });
        login.setOnClickListener(v -> checkUsername());

    }

    private void checkUsername() {
        boolean isValid = true;
        if (isEmpty(user)){
            user.setError("This input must be filled");
            isValid = false;
        }
        else
        {
            user.setError(null);
        }
        if (isEmpty(pass)){
            pass.setError("This input must be filled");
            isValid = false;
        }
        else{
            pass.setError(null);
        }

        if (isValid) {

            String usrnm = user.getText().toString().trim();
            String pswrd = pass.getText().toString().trim();


            if (dbUser.checkUser(usrnm,pswrd)){

                String userId = DBUser.getUserId();
                sharedPreferences.edit().putString("userId",userId).apply();
                sharedPreferences.edit().putBoolean("logged",true).apply();
                moveActivity();
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Username or Password invalid", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }

    private void moveActivity(){
        Intent intent = new Intent(MainActivity.this, KosList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()){
            toastBack.cancel();
            super.onBackPressed();
            return;
        }
        else{
            toastBack = Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            toastBack.show();
        }

        backPressed = System.currentTimeMillis();
    }
}
