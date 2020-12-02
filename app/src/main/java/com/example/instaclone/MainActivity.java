package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signupBtn,loginBtn;
    private EditText usernameTxt,emailTxt,passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");
        signupBtn = findViewById(R.id.signup_btn);
        loginBtn = findViewById(R.id.login_btn);

        usernameTxt = findViewById(R.id.username_txt);
        emailTxt = findViewById(R.id.email_txt);
        passwordTxt = findViewById(R.id.password_txt);
        passwordTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER &&
                    event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(signupBtn);
                }
                return false;
            }
        });
        signupBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            trasitionToSocialMediaActivty();
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_btn:
                if(usernameTxt.getText().toString().equals("") ||
                        emailTxt.getText().toString().equals("") ||
                        passwordTxt.getText().toString().equals("")){
                    FancyToast.makeText(MainActivity.this,"Email, Username, Password is required !",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                }
                else{

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing Up " + usernameTxt.getText().toString());
                progressDialog.show();
                final ParseUser user = new ParseUser();
                user.setUsername(usernameTxt.getText().toString());
                user.setEmail(emailTxt.getText().toString());
                user.setPassword(passwordTxt.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(MainActivity.this,"Success",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            progressDialog.dismiss();
                            trasitionToSocialMediaActivty();
                        }
                        else{
                            FancyToast.makeText(MainActivity.this,"Something Went Wrong !",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                            progressDialog.dismiss();

                        }
                    }
                });
                }

                break;
            case R.id.login_btn:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    public void signUpLayoutIsClicked(View v){
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