package com.it.sumuk.illinitower;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {

    private TextView nameLabel;
    private TextView flexLabel;
    private TextView unitLabel;
    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    public static String currentUserID;
    public static String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        final ImageView dining = (ImageView) findViewById(R.id.imgDining);
        final ImageView diningmenu = (ImageView) findViewById(R.id.imgDailyMenu);
        final ImageView portal = (ImageView) findViewById(R.id.imgPortal);
        final ImageView bulletin = (ImageView) findViewById(R.id.imgBulletin);
        final ImageView signout = (ImageView) findViewById(R.id.imgSignOut);
        final ImageView packages = (ImageView) findViewById(R.id.imgPackages);
        final ImageView contact = (ImageView) findViewById(R.id.imgContact);
        roomNumber = "None";
        nameLabel = (TextView) findViewById(R.id.txtName);
        flexLabel = (TextView) findViewById(R.id.txtFlexHome);
        unitLabel = (TextView) findViewById(R.id.txtUnit);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String unitNumber = dataSnapshot.child(currentUserID).child("Unit").getValue().toString();
                    String flexNumber = dataSnapshot.child(currentUserID).child("Flex").getValue().toString();
                    String firstName = dataSnapshot.child(currentUserID).child("FirstName").getValue().toString();

                    firstName = "Welcome, " + firstName + "!";
                    roomNumber = unitNumber;
                    unitNumber = "Unit: " + unitNumber;
                    flexNumber = "IlliniFlex: $" + flexNumber;

                    nameLabel.setText(firstName);
                    flexLabel.setText(flexNumber);
                    unitLabel.setText(unitNumber);
                } catch(Exception e){
                    String firstName = "Welcome, Guest!";
                    String unitNumber = "Unit: None";
                    roomNumber = "None";
                    String flexNumber = "IlliniFlex: $0.00";

                    nameLabel.setText(firstName);
                    flexLabel.setText(flexNumber);
                    unitLabel.setText(unitNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dining.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, DiningMain.class);
                startActivity(intent);
            }
        });

        diningmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Dining.class);
                startActivity(intent);
            }
        });

        portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, PortalMain.class);
                startActivity(intent);
            }
        });

        bulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, BulletinMain.class);
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainPage.this);
                adb.setTitle("Are you sure you want to Sign Out?");
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainPage.this, LogIn.class);
                        startActivity(intent);
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
            }
        });

        packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("Packages");
                myRef.addValueEventListener(new ValueEventListener() {
                    boolean found = false;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                            String room = "";
                            try{
                                room = item_snapshot.getKey();
                            }catch (Exception e){
                                room = "No packages to pick-up.";
                            }
                            if (room.equals("No packages to pick-up.")){
                                Toast.makeText(MainPage.this, room,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (room.equals(roomNumber)){
                                    String packagespickup = item_snapshot.getValue().toString();
                                    packagespickup = "You have " + packagespickup + " package(s) to pick-up.";
                                    Toast.makeText(MainPage.this, packagespickup,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if (found == false){
                            Toast.makeText(MainPage.this, "You have 0 package(s) to pick-up.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError){

                    }
                });
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainPage.this);
                builder
                        .setTitle("Contact Us")
                        .setMessage("Leasing: 217.814.0000\nResidents: 217.344.0400\nFax: 217.344.8162\nE-mail: info@illinitower.net")
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
