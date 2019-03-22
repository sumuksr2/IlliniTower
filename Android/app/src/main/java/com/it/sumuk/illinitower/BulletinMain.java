package com.it.sumuk.illinitower;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BulletinMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        myref = FirebaseDatabase.getInstance().getReference().child("Bulletin");

        FirebaseRecyclerOptions<Bulletin> options = new FirebaseRecyclerOptions.Builder<Bulletin>().setQuery(myref, Bulletin.class).build();

        String test = "onCreate";
        Toast.makeText(BulletinMain.this, test,
                Toast.LENGTH_SHORT).show();

        FirebaseRecyclerAdapter<Bulletin,BulletinViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Bulletin,BulletinViewHolder>(options) {
            @NonNull
            @Override
            public BulletinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.individual_row, viewGroup, false);

                String test = "onCreateViewHolder";

                Toast.makeText(BulletinMain.this, test,
                        Toast.LENGTH_SHORT).show();

                return new BulletinViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BulletinViewHolder holder, int position, @NonNull Bulletin model) {
                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setImage(model.getImage());

                String test = "onBindViewHolder";

                Toast.makeText(BulletinMain.this, test,
                        Toast.LENGTH_SHORT).show();

            }
        };

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        transparentStatusAndNavigation();
    }

    public static class BulletinViewHolder extends RecyclerView.ViewHolder{
        TextView text_title, text_description;
        ImageView cardimage;
        View mView;
        public BulletinViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            text_title = (TextView) itemView.findViewById(R.id.bulletintitle);
            text_description = (TextView) itemView.findViewById(R.id.bulletindescription);
            cardimage = (ImageView) itemView.findViewById(R.id.bulletinimage);
        }

        public void setTitle(String title) {
            text_title.setText(title);
        }

        public void setDescription(String description){
            text_description.setText(description);
        }

        public void setImage(String image){
            Picasso.with(mView.getContext())
                    .load(image)
                    .into(cardimage);
        }
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
