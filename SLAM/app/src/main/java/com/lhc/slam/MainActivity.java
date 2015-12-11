package com.lhc.slam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imageSrc,imageDepth;
    private Button buttonStart;
    private TextView txInfo;
    private Bitmap bitmapSrc, bitmapDepth;
    //private Mat matSrc, matDepth;
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
        txInfo = (TextView)findViewById(R.id.textView);
        isFirst = true;

    }


    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {


            if(isFirst)
                matData = (long[])LibImgFun.getMatDataFromCpp().clone();
                isFirst = false;
                txInfo.setText("ok");
                MyThread myThread = new MyThread();
                myThread.start();
                handler.post(task);//立即显示图片
        }
    }


    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {
            // TODO

            //读取地址中的原图
            Mat matSrc = new Mat(matData[0]);
            if(matSrc.cols()>0 && matSrc.rows() >0) {
                bitmapSrc = Bitmap.createBitmap(matSrc.cols(), matSrc.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(matSrc, bitmapSrc);
                imageSrc.setImageBitmap(bitmapSrc);
            }
            else
            {
                System.out.println("no data");
            }

            //读取地址中的深度图
            Mat matDepth = new Mat(matData[1]);
            if(matDepth.cols()>0 && matDepth.rows()> 0){
                bitmapDepth=Bitmap.createBitmap(matDepth.cols(), matSrc.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(matDepth ,bitmapDepth);
                imageDepth.setImageBitmap(bitmapDepth);
            }
            handler.postDelayed(this, 1000);//设置延迟时间,50ms



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
    class MyThread extends Thread{
        @Override
        public void run() {
            LibImgFun.startWork();
        }
    }
}
