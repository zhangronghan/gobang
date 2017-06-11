package com.example.administrator.gobang;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyView extends View{
    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE=10;
    private Paint mPaint;
    private Bitmap mWhiteBitmap;
    private Bitmap mBlackBitmap;
    private float radioBitmapOfLineHeight=3 *1.0f/ 4;

    //白棋先手 轮到白棋
    private Boolean mIsWhite=true;
    private ArrayList<Point> mWhiteList=new ArrayList<>();
    private ArrayList<Point> mBlackList=new ArrayList<>();

    private Boolean mIsGameOver=false;
    private Boolean mIsWhiteWin;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0x90000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mWhiteBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
        mBlackBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(mIsGameOver){
                    return false;
                }
                int x= (int) event.getX();
                int y= (int) event.getY();
                Point point=getPiesePoint(x,y);
                //避免棋子落在已有的棋子上
                if(mWhiteList.contains(point) || mBlackList.contains(point)){
                    return false;
                }
                if(mIsWhite){
                    mWhiteList.add(point);
                } else {
                    mBlackList.add(point);
                }
                invalidate();
                mIsWhite = !mIsWhite;
                break;

            default:
                break;

        }
        return true;
    }

    private Point getPiesePoint(int x, int y) {
        Point point=new Point((int) (x / mLineHeight),(int)(y / mLineHeight));
        return point;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasure=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasure=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        int width=Math.min(widthMeasure,heightMeasure);
        if(widthMode==MeasureSpec.UNSPECIFIED){
            width=heightMeasure;
        } else if(heightMode==MeasureSpec.UNSPECIFIED){
            width=widthMeasure;
        }
        setMeasuredDimension(width,width);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        mPanelWidth = w;
        mLineHeight= mPanelWidth *1.0f/MAX_LINE;

        int newWidth= (int) (mLineHeight * radioBitmapOfLineHeight);

        mWhiteBitmap=Bitmap.createScaledBitmap(mWhiteBitmap,newWidth,newWidth,false);
        mBlackBitmap=Bitmap.createScaledBitmap(mBlackBitmap,newWidth,newWidth,false);
    }

    public void ReStart(){
        mWhiteList.clear();
        mBlackList.clear();
        mIsGameOver=false;
        mIsWhiteWin=false;
        invalidate();
    }

    public void gameBack(){
        if(mIsWhite){
            //悔棋 黑棋
            findGameEnd(mBlackList);
        } else {
            //悔棋 白棋
            findGameEnd(mWhiteList);
        }
    }

    private void findGameEnd(ArrayList<Point> mList){
        for(int i=0,n=mList.size();i<n;i++){
            if(i==n-1 && i>=0){
                mList.remove(i);
                invalidate();
                mIsWhite=!mIsWhite;
            }
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMyLine(canvas);
        drawPieses(canvas);

        checkGameState();
    }

    private void checkGameState() {
        Boolean mWhiteWin=checkFiveinPiecesLine(mWhiteList);
        Boolean mBlackWin=checkFiveinPiecesLine(mBlackList);

        if(mWhiteWin || mBlackWin){
            mIsGameOver=true;
            mIsWhiteWin=mWhiteWin;

            String text=mIsWhiteWin?"白棋":"黑棋";
            new AlertDialog.Builder(getContext()).setMessage(text+"胜利")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private Boolean checkFiveinPiecesLine(List<Point> pointList) {
        for(Point p : pointList){
            int x=p.x;
            int y=p.y;

            boolean win=fivePiecesUtils.checkHorizontalPiece(x,y,pointList);
            if(win) return true;
            win = fivePiecesUtils.checkVerticalPiece(x,y,pointList);
            if(win) return true;
            win = fivePiecesUtils.checkLeftDiagonalPiece(x,y,pointList);
            if(win) return true;
            win = fivePiecesUtils.checkRightDiagonalPiece(x,y,pointList);
            if(win) return true;

        }
        return false;
    }


    private void drawPieses(Canvas canvas) {
        for(int i=0,n=mWhiteList.size(); i<n; i++){
            Point whitePoint=mWhiteList.get(i);
            canvas.drawBitmap(mWhiteBitmap,
                    (whitePoint.x+(1-radioBitmapOfLineHeight)/2)*mLineHeight,
                    (whitePoint.y+(1-radioBitmapOfLineHeight)/2)*mLineHeight,null);
        }
        for(int i=0, n=mBlackList.size();i<n;i++){
            Point blackPoint=mBlackList.get(i);
            canvas.drawBitmap(mBlackBitmap,
                    (blackPoint.x+(1-radioBitmapOfLineHeight)/2)*mLineHeight,
                    (blackPoint.y+(1-radioBitmapOfLineHeight)/2)*mLineHeight,null);
        }

    }

    private void drawMyLine(Canvas canvas) {
        int w=mPanelWidth;
        float mLine=mLineHeight;
        for(int i=0;i<MAX_LINE;i++){
            int startX= (int) (mLine / 2);
            int endX= (int) (w- mLine / 2);
            int y= (int) ((0.5+i)*mLine);
            canvas.drawLine(startX,y, endX,y,mPaint);
            canvas.drawLine(y,startX,y,endX,mPaint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantData.INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(ConstantData.INSTANCE_GAME_OVER,mIsGameOver);
        bundle.putParcelableArrayList(ConstantData.INSTANCE_WHITE_LIST,mWhiteList);
        bundle.putParcelableArrayList(ConstantData.INSTANCE_BLACK_LIST,mBlackList);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            mIsGameOver=bundle.getBoolean(ConstantData.INSTANCE_GAME_OVER);
            mWhiteList=bundle.getParcelableArrayList(ConstantData.INSTANCE_WHITE_LIST);
            mBlackList=bundle.getParcelableArrayList(ConstantData.INSTANCE_BLACK_LIST);
            super.onRestoreInstanceState(bundle.getParcelable(ConstantData.INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }



}
