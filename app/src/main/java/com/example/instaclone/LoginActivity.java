package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button loginBtn,signupBtn;
    private EditText usernameLoginTxt,passwordLoginTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        loginBtn = findViewById(R.id.login_login_btn);
        signupBtn = findViewById(R.id.signup_login_btn);
        usernameLoginTxt = findViewById(R.id.username_login_txt);
        passwordLoginTxt = findViewById(R.id.password_login_txt);
        passwordLoginTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER &&
                        event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(loginBtn);
                }
                return false;
            }
        });
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            trasitionToSocialMediaActivty();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_login_btn:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_login_btn:
                if(usernameLoginTxt.getText().toString().equals("") ||
                        passwordLoginTxt.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this,"Username, Password must be entered!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                }
                else{

                ParseUser.logInInBackground(usernameLoginTxt.getText().toString(), passwordLoginTxt.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user!=null && e==null){
                                    FancyToast.makeText(LoginActivity.this,"Welcome"+ user.getUsername() +"!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                    trasitionToSocialMediaActivty();
                                }
                                else{
                                    FancyToast.makeText(LoginActivity.this,"Something Went Wrong !",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                                }
                            }
                        });
                }

                break;
        }
    }
    public void loginLayoutIsClicked(View view){
        try{
            InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){

        }
    }
    private void trasitionToSocialMediaActivty(){
        Intent intent = new Intent(this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}