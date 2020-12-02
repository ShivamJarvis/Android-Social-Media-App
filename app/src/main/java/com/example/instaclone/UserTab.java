package com.example.instaclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UserTab extends Fragment {


    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;
    public UserTab() {
        // Required empty public constructor
    }



//    public static UserTab newInstance(String param1, String param2) {
//        UserTab fragment = new UserTab();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_tab, container, false);
        listView = view.findViewById(R.id.list_view);
        ParseQuery<ParseUser> parseQuery = (ParseUser.getQuery());
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){
                    if(users.size()>0){
                        for(ParseUser parseUser:users){
                            arrayList.add(parseUser.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        progressDialog.dismiss();
                    }
                }
            }
        });

        return  view;
    }
}