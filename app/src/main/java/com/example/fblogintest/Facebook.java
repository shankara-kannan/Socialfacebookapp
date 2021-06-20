package com.example.fblogintest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

public class Facebook extends AppCompatActivity {
    private Button logout;
    private ImageView ivprofile;
    private TextView tvname;
    private TextView tvemail;
    private TextView tvidnumber;
    private String name;
    private String email;
    private String idnumber;
    private String imageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        tvname = (TextView) findViewById(R.id.textView);
        tvidnumber = (TextView) findViewById(R.id.textView2);
        tvemail = (TextView) findViewById(R.id.textView4);
        ivprofile = (ImageView) findViewById(R.id.imageView);
        Bundle ibundle = getIntent().getExtras();
        name = ibundle.getString("name");
        email = ibundle.getString("email");
        idnumber = ibundle.getString("id");
        imageurl = ibundle.getString("imageurl");
        tvname.setText(name);
        tvemail.setText(email);
        tvidnumber.setText(idnumber);

        Bundle params = new Bundle();
        params.putBoolean("redirect", false);
        params.putString("type", "large");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/picture",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {
                            String str_facebookimage = (String) response.getJSONObject().getJSONObject("data").get("url");

                            Glide.with(com.example.fblogintest.Facebook.this).load(str_facebookimage).skipMemoryCache(true).into(ivprofile);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();

        logout = (Button) findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(com.example.fblogintest.Facebook.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }
}