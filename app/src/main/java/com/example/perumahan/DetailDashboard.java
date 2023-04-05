package com.example.perumahan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class DetailDashboard extends AppCompatActivity {
    ImageButton belakang,maju;
    ImageSwitcher action_image;


    int index=0;
    int fongto[]={R.drawable.fotodash,R.drawable.realestate};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dashboard);

        belakang=findViewById(R.id.belakang);
        maju=findViewById(R.id.maju);
        action_image=findViewById(R.id.action_image);

        belakang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                -- index;
                if(index<0){
                    index=fongto.length-1;
                }
                action_image.setImageResource(fongto[index]);
            }
        });
        maju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index==fongto.length){
                    index=0;
                }
                action_image.setImageResource(fongto[index]);
            }
        });

        action_image.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(300);
                imageView.setMaxHeight(300);

                return imageView;
            }
        });

        action_image.setImageResource(fongto[index]);
        
    }
}