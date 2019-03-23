package com.it.sumuk.illinitower;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DiningMain extends AppCompatActivity {

    private CardView swipeBar;
    private TextView swipeslabel;
    private ImageView dailymenu;
    private ImageView chef;
    private ImageView feedback;
    private ImageView hours;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    public static String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_main);

        swipeBar = (CardView) findViewById(R.id.cvMealSwipes);
        swipeslabel = (TextView) findViewById(R.id.txtSwipes);
        dailymenu = (ImageView) findViewById(R.id.imgdailymenu);
        chef = (ImageView) findViewById(R.id.imgchef);
        feedback = (ImageView) findViewById(R.id.imgfeedback);
        hours = (ImageView) findViewById(R.id.imghours);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String swipes = dataSnapshot.child(currentUserID).child("Meals").getValue().toString();
                    swipeslabel.setText(swipes);
                    int swipesint = Integer.parseInt(swipes);
                    int calculationtemp = 200 - swipesint;
                    int marginset = (int) (calculationtemp * 3);
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) swipeBar.getLayoutParams();
                    lp.setMargins(5, 5, marginset, 5);
                    swipeBar.setLayoutParams(lp);
                }catch (Exception e){
                    String swipes = "0";
                    swipeslabel.setText(swipes);
                    int swipesint = Integer.parseInt(swipes);
                    int calculationtemp = 200 - swipesint;
                    int marginset = (int) (calculationtemp * 3);
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) swipeBar.getLayoutParams();
                    lp.setMargins(5, 5, marginset, 5);
                    swipeBar.setLayoutParams(lp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dailymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiningMain.this, Dining.class);
                startActivity(intent);
            }
        });

        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiningMain.this);
                builder
                        .setTitle("Dining Hours")
                        .setMessage("Breakfast: 7-10AM\nLunch: 11AM-2PM\nDinner: 5-8PM")
                        .setPositiveButton("Close", null)
                        .show();
            }
        });



        transparentStatusAndNavigation();
    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
