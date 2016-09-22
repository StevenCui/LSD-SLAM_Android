package org.opencv.samples.tutorial2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by lhc on 2016/5/25.
 */
public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		/*
		 * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
		 * drawLine 绘制直线 drawPoin 绘制点
		 */
        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色

        canvas.drawText("画线及弧线：", 10, 60, p);
        p.setColor(Color.GREEN);// 设置绿色

        float x1 = 200+LibImgFun.getAY()*3;
        float x2 = 600+LibImgFun.getAY()*3;//左右
        float y1 = 450-LibImgFun.getAX()*3;
        float y2 = 150-LibImgFun.getAX()*3;//上下
        canvas.drawLine(400, 400,x1, y1, p);// 画线
        canvas.drawLine(400, 400, x1,y2, p);// 斜线
        canvas.drawLine(400, 400,x2,y1, p);// 画线
        canvas.drawLine(400, 400, x2,y2, p);// 画线
        canvas.drawLine(x1, y1,x1, y2, p);// 画线
        canvas.drawLine(x1, y1,x2, y1, p);// 画线
        canvas.drawLine(x2, y2,x1, y2,  p);// 画线
        canvas.drawLine(x2, y2,x2, y1, p);// 画线

    }

}
