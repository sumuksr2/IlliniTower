package com.it.sumuk.illinitower;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PortalMain extends AppCompatActivity {

    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    public static String currentUserID;
    private TextView balance;
    private TextView date;
    private ImageView packages;
    public static String roomNumber;
    private ImageView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_main);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();
        balance = (TextView) findViewById(R.id.txtBalance);
        date = (TextView) findViewById(R.id.txtDueDate);
        packages = (ImageView) findViewById(R.id.imgPackagesPortal);
        roomNumber = "None";
        contact = (ImageView) findViewById(R.id.imgContactPortal);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    String myBalance = dataSnapshot.child(currentUserID).child("Balance").getValue().toString();
                    myBalance = "$" + myBalance;
                    balance.setText(myBalance);

                    String myDate = dataSnapshot.child(currentUserID).child("Date").getValue().toString();
                    myDate = "Due Date: " + myDate;
                    date.setText(myDate);

                    roomNumber = dataSnapshot.child(currentUserID).child("Unit").getValue().toString();
                }catch (Exception e){
                    String myBalance = "0.00";
                    myBalance = "$" + myBalance;
                    balance.setText(myBalance);

                    String myDate = "00/00/0000";
                    myDate = "Due Date: " + myDate;
                    date.setText(myDate);

                    roomNumber = dataSnapshot.child(currentUserID).child("Unit").getValue().toString();

                    Toast.makeText(PortalMain.this, "Please Log In for access.",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PortalMain.this, MainPage.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                                Toast.makeText(PortalMain.this, room,
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (room.equals(roomNumber)){
                                    String packagespickup = item_snapshot.getValue().toString();
                                    packagespickup = "You have " + packagespickup + " package(s) to pick-up.";
                                    Toast.makeText(PortalMain.this, packagespickup,
                                            Toast.LENGTH_SHORT).show();
                                    found = true;
                                }
                            }
                        }
                        if (found == false){
                            Toast.makeText(PortalMain.this, "You have 0 package(s) to pick-up.",
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
                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PortalMain.this);
                        builder
                                .setTitle("Contact Us")
                                .setMessage("Leasing: 217.814.0000\nResidents: 217.344.0400\nFax: 217.344.8162\nE-mail: info@illinitower.net")
                                .setPositiveButton("Close", null)
                                .show();
                    }
                });
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
