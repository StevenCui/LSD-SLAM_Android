package org.opencv.samples.tutorial2;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class Tutorial2Activity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2{
    private ImageView imageDepth;
    private CameraBridgeViewBase imageSrc;
    private Button buttonStart;

    private Bitmap bitmapSrc, bitmapDepth;
    private TextView textview;
    private boolean isFirst;
    private Mat matSrc;
    private Mat matDepth ;
    private Mat matDepthCopy,matRe;
    private long addrSrc, addrDepth;



    //画图
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset; //
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private Context context;


    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    System.loadLibrary("mixed_sample");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    /*static {
       System.loadLibrary("mixed_sample");
    }*/
    DrawView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tu);
        this.setTitle("LSD-SLAM");

        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new ClickEvent());
        textview=(TextView)findViewById(R.id.textView);
        imageSrc = (CameraBridgeViewBase)findViewById(R.id.imageViewSrc);
        imageDepth = (ImageView)findViewById(R.id.imageViewDepth);

        imageSrc.setCvCameraViewListener(this);
        isFirst = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        imageSrc.enableView();

        //画图
        mDataset = new XYMultipleSeriesDataset();
        series = new XYSeries("sensor");
        mDataset.addSeries(series);
        // renderer - 渲染器
        renderer = buildRenderer(Color.WHITE, PointStyle.CIRCLE);
        // 设置图标样式
        setChartSettings(renderer, "T", "LEVEL", -20, 20, -20, 20, Color.GREEN);
        // 生成图表
        chart = ChartFactory.getLineChartView(Tutorial2Activity.this, mDataset,
                renderer);
        // 将图表添加到布局中去
        layout.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        renderer.setYAxisMin(-20);
        renderer.setYAxisMax(20);


        LinearLayout layoutPose=(LinearLayout) findViewById(R.id.linearLayoutPose);
        view=new DrawView(this);

        //通知view组件重绘
        view.invalidate();
        layoutPose.addView(view);




    }
    @Override
    public void onPause()
    {
        super.onPause();
       /*if (imageSrc != null)
           imageSrc.disableView();*/
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        /*if (imageSrc != null)
           imageSrc.disableView();*/
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        matSrc = new Mat();
        matDepth = new Mat();
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        inputFrame.gray().copyTo(matSrc);
        return  inputFrame.gray();

    }


    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (isFirst) {
                MyThread myThread = new MyThread();
                myThread.start();
                isFirst = false;
                handler.post(task);
            }
        }
    }


    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {
            // TODO
            //读取地址中的深度图


            /*if(matSrc.cols()>0 && matSrc.rows() >0) {
                bitmapSrc = Bitmap.createBitmap(matSrc.cols(), matSrc.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(matSrc, bitmapSrc);
                imageSrc.setImageBitmap(bitmapSrc);
            }
            else
            {
                System.out.println("no data");
            }*/

            //读取地址中的深度图

            matDepthCopy = matDepth ;
            if(matDepthCopy.cols()>0 && matDepthCopy.rows()> 0){
                bitmapDepth=Bitmap.createBitmap(matDepthCopy.cols(), matDepthCopy.rows(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(matDepthCopy ,bitmapDepth);
                imageDepth.setImageBitmap(bitmapDepth);
            }
            else {
                System.out.println("no data");

            }
            updateUI(LibImgFun.getX(), LibImgFun.getY());
            view.invalidate();
          textview.setText(LibImgFun.getAX() + "  " +LibImgFun.getAY() + "  " +LibImgFun.getAZ());
            handler.postDelayed(this, 150);//设置延迟时间,50ms*/
        }
    };

    class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println(matSrc.getNativeObjAddr() + "////////////////////");
            LibImgFun.startWork(matSrc.getNativeObjAddr(), matDepth.getNativeObjAddr());
        }
    }


    private void updateUI(float x , float y) {
        int length = series.getItemCount();
        if (length > 500) {
            series.clear();
        }
        mDataset.removeSeries(series);
  /*      int length = series.getItemCount();
        if (length > 50) {
            length = 50;
        }
        for (int j = 0; j < length; j++) {
            // X坐标每次的增量 - 1
            xx[j] = (int) series.getX(j) - 1;
            yy[j] = (float) series.getY(j);
        }
        series.clear();*/

        // 新出来的点肯定首先画，加到第一个
        series.add(x, y);
/*        for (int k = 0; k < length; k++) {
            series.add(xx[k], yy[k]);
        }*/
        mDataset.addSeries(series);
        chart.invalidate();
    }

    // 设置渲染器
    protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        // 设置图表中折线的样式
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(color); // 线条颜色
        r.setPointStyle(style); // 点样式
        r.setLineWidth(3); // 线宽

        renderer.addSeriesRenderer(r); // 添加
        return renderer;
    }

    // 设置图表的显示
    protected void setChartSettings(XYMultipleSeriesRenderer renderer,
                                    String xTitle, String yTitle, double xMin, double xMax,
                                    double yMin, double yMax, int axesColor) {

        renderer.setChartTitle("");
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        // renderer.setLabelsColor(labelsColor);
        renderer.setShowGrid(true); // 是否显示网格
        renderer.setGridColor(Color.RED); // 网格的颜色
        renderer.setXLabels(20);
        renderer.setYLabels(10);
        renderer.setXTitle(""); // 设置title
        renderer.setYTitle("");
        renderer.setYLabelsAlign(Paint.Align.RIGHT); // Y周文字对齐方式
        renderer.setPointSize((float) 2);
        // renderer.setShowLegend(false);
        renderer.setLabelsTextSize(20);
        renderer.setLabelsColor(Color.GREEN);
        // renderer.setLegendTextSize(15);
        renderer.setChartTitleTextSize(30);
        renderer.setAxisTitleTextSize(30);
        renderer.setBackgroundColor(Color.parseColor("#00000000"));
        renderer.setMarginsColor(Color.argb(0, 0xF3, 0xF3, 0xF3)); // 图表与周围四周的颜色
        renderer.setMargins(new int[] { 20, 30, 15, 20 });
        // 设置图表的边距renderer.setMargins(newint[] { 20, 30,15, 20 }); //设置图表的边距
    }


}