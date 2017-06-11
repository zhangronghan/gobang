package com.example.administrator.gobang;

import android.graphics.Point;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class fivePiecesUtils {
    public static int MAX_PIECES=5;

    public static boolean checkHorizontalPiece(int x, int y, List<Point> pointList) {
        int count=1;
        //左
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x-i,y))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        //右
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x+i,y))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        return false;
    }


    public static boolean checkVerticalPiece(int x, int y, List<Point> pointList) {
        int count=1;
        //上
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x,y-i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        //下
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x,y+i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        return false;
    }


    public static boolean checkLeftDiagonalPiece(int x, int y, List<Point> pointList) {
        int count=1;
        //左上
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x-i,y-i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        //左下
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x-i,y+i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        return false;
    }

    public static boolean checkRightDiagonalPiece(int x, int y, List<Point> pointList) {
        int count=1;
        //右上
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x+i,y-i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        //右下
        for(int i=1;i<MAX_PIECES;i++){
            if(pointList.contains(new Point(x+i,y+i))){
                count++;
            } else {
                break;
            }
        }
        if(count==5){
            return true;
        }
        return false;
    }



}
