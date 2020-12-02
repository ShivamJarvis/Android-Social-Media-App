package com.example.instaclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment {

    private EditText profileNameEditText,bioEditText,proffesionEditText,hobbiesEditText,favSportEditText;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }




//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);
        profileNameEditText = view.findViewById(R.id.profile_name);
        proffesionEditText = view.findViewById(R.id.pro_text);
        hobbiesEditText = view.findViewById(R.id.hobbies_text);
        bioEditText = view.findViewById(R.id.bio_text);
        favSportEditText = view.findViewById(R.id.fav_sport_text);
        btnUpdateInfo = view.findViewById(R.id.btn_update_info);
        ParseUser user = ParseUser.getCurrentUser();
        if(user.get("profile_name")!=null){
            profileNameEditText.setText(user.get("profile_name").toString());
        }
        if(user.get("profile_profession")!=null){
            proffesionEditText.setText(user.get("profile_profession").toString());
        }
        if (user.get("profile_hobbies")!=null){
            hobbiesEditText.setText(user.get("profile_hobbies").toString());
        }
        if(user.get("profile_bio")!=null){
            bioEditText.setText(user.get("profile_bio").toString());
        }

        if(user.get("profile_fav_sport")!=null){
            favSportEditText.setText(user.get("profile_fav_sport").toString());
        }

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.put("profile_name",profileNameEditText.getText().toString());
                user.put("profile_bio",bioEditText.getText().toString());
                user.put("profile_profession",proffesionEditText.getText().toString());
                user.put("profile_fav_sport",favSportEditText.getText().toString());
                user.put("profile_hobbies",hobbiesEditText.getText().toString());

                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),"Profile Updated Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                        }
                        else{
                            FancyToast.makeText(getContext(),"Something went wrong",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                        }
                    }
                });
            }
        });
        return view;
    }
}