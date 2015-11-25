package com.lhc.slam;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {
    private ImageView imageSrc,imageDepth;
    private Button buttonStart;
    private Bitmap bitmapSrc, bitmapDepth;
    private Mat matSrc, matDepth;
    private long addressSrc , addressDepth;
    private long[] addressArray = new long[3];//addressArray[0] for imageSource,addressArray[1] for imageDepth,addressArray[2] for infomation

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
    }


    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            addressArray= (long[])LibImgFun.getImageAddressFromCpp().clone();

            if(addressArray[0] != 0 && addressArray[1] != 0 &&addressArray[2] != 0)
                handler.post(task);//立即显示图片
            else
               System.out.println("初始化失败");
        }
    }


    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {
            // TODO
            handler.postDelayed(this,1000);//设置延迟时间,50ms

            //读取地址中的原图
            /*matSrc = new Mat(addressSrc);
            Utils.matToBitmap(matSrc ,bitmapSrc);
            imageSrc.setImageBitmap(bitmapSrc);

            //读取地址中的深度图
            matDepth = new Mat(addressDepth);
            Utils.matToBitmap(matDepth ,bitmapDepth);
            imageDepth.setImageBitmap(bitmapDepth);*/

        }
    };
}
