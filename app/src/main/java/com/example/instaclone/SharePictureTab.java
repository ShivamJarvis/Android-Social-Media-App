package com.example.instaclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


public class SharePictureTab extends Fragment {

    private ImageView imgShare;
    private EditText descTxt;
    private Button shareBtn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Bitmap bitmap;
    public SharePictureTab() {
        // Required empty public constructor
    }



//    public static SharePictureTab newInstance(String param1, String param2) {
//        SharePictureTab fragment = new SharePictureTab();
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
        View view =  inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        shareBtn = view.findViewById(R.id.share_btn);
        imgShare = view.findViewById(R.id.img_share);
        descTxt = view.findViewById(R.id.desc_txt);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap!=null){
                    if(descTxt.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Description Must be entered",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }else{
                        //FancyToast.makeText(getContext(),"Something Went Wrong",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("desription",descTxt.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Uploading");
                        progressDialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    FancyToast.makeText(getContext(),"Uploaded Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    progressDialog.dismiss();
                                }
                                else{
                                    progressDialog.dismiss();
                                }
                            }
                        });


                    }

                } else {
                    FancyToast.makeText(getContext(),"Something Went Wrong no bitmap",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else{
                    getCurrentImage();
                }
            }
        });

        return  view;
    }

    private void getCurrentImage() {
        //FancyToast.makeText(getContext(),"Now We can access the Images",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK){
                try{

                    Uri uri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(uri,filePathColumn,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    imgShare.setImageBitmap(bitmap);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000 && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getCurrentImage();
            }
        }
    }
}