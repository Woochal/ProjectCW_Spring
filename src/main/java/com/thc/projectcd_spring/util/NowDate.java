package com.thc.projectcd_spring.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NowDate {

    // 현재 시간을 반환하는 메서드
    public String getNow(){
        String[] nowDateString = {"",""};

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //현재시간
        return simpleDateFormat.format(nowDate);
    }

    // 만료 시간을 생성해서 반환하는 메서드
    public String getDue(int second){
        String nowDateString = "";

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        //시간 더하기
        cal.add(Calendar.SECOND, second);
        nowDateString = simpleDateFormat.format(cal.getTime());

        return nowDateString;
    }
}
