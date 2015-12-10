package com.lhc.slam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imageSrc,imageDepth;
    private Button buttonStart;
    private Bitmap bitmapSrc, bitmapDepth;
    private Mat matSrc, matDepth;
    private boolean isFirst;
    private long[] matData = new long[2];//matData[0] for image_src ,matData[1] for image_depth
    private int i = 0;

    private String pathString;

    static {
        System.loadLibrary("opencv_java");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("LSD-SLAM");
        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new ClickEvent());
        imageSrc = (ImageView)findViewById(R.id.imageViewSrc);
        imageDepth = (ImageView)findViewById(R.id.imageViewDepth);
        isFirst = true;

    }


    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            //addressArray= (long[])LibImgFun.getImageAddressFromCpp().clone();

            if(!isFirst)
                LibImgFun.startWork();

        }
    }


    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {
            // TODO
            handler.postDelayed(this,50);//设置延迟时间,50ms

            matData = (long[])LibImgFun.getMatDataFromCpp().clone();

            //读取地址中的原图
            matSrc = new Mat(matData[0]);
            Utils.matToBitmap(matSrc, bitmapSrc);
            imageSrc.setImageBitmap(bitmapSrc);

            //读取地址中的深度图
            matDepth = new Mat(matData[1]);
            Utils.matToBitmap(matDepth ,bitmapDepth);
            imageDepth.setImageBitmap(bitmapDepth);
            /*if(i<75)
                i++;
            else
                i=1;
            pathString = "mnt/sdcard/FilesForLSDSLAM/" + String.format("%05d", i)+".png";
            imageSrc.setImageBitmap(MainActivity.getDiskBitmap(pathString));*/

        }
    };



    private static Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return bitmap;
    }
}
