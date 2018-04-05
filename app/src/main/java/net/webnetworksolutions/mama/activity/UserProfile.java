package net.webnetworksolutions.mama.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import net.webnetworksolutions.mama.R;

import java.io.InputStream;

/**
 * Created by Benja on 4/2/2018.
 */

public class UserProfile extends AppCompatActivity {

    private ShareDialog shareDialog;
    private Button logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.facebook_user_profile);
        Toolbar toolbar= (Toolbar) findViewById(R.id.facebook_toolbar);

        shareDialog= new ShareDialog(this);
        FloatingActionButton fab= findViewById(R.id.fab_facebook);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content= new ShareLinkContent.Builder().build();
                shareDialog.show(content);
            }
        });
        Bundle inBundle= getIntent().getExtras();
        String name= inBundle.get("name").toString();
        String surname= inBundle.get("surname").toString();
        String imageUrl= inBundle.get("imageUrl").toString();

        TextView nameView= findViewById(R.id.nameAndSurname);
        nameView.setText("" +name +" "+ surname);
        Button logout= findViewById(R.id.facebook_logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent login= new Intent(UserProfile.this, Login2Activity.class);
                startActivity(login);
                finish();
            }
        });
        new UserProfile.DownloadImage((ImageView) findViewById(R.id.facebook_profie_image)).execute(imageUrl);
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay= urls[0];
            Bitmap mIconll= null;
            try {
                InputStream in= new java.net.URL(urldisplay).openStream();
                mIconll= BitmapFactory.decodeStream(in);
            } catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIconll;
        }
        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }
}
