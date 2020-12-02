package com.example.instaclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("foRB5OCTqODZqWmOtFO9D6leOnkOVebsIUNUyXDX")
                // if defined
                .clientKey("cJxaMhMSamPGuH0Ymb4litW0IypvCAEw6Lvg7GHT")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
