package com.example.newopenapiexchangeproject3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GlobalTime {

    public static String newstime2;
    public static Date date2;
    public static SimpleDateFormat sdf2;

    public static TimeZone[] timeZone2 = new TimeZone[22];
    public static DateFormat[] dateFormat2 = new DateFormat[22];

    public static String noetime;
    public static Date notedate;
    public static SimpleDateFormat notesdf;


    public static  String[] timedifferent = new String[22];
    Date curDate1;
    SimpleDateFormat dateFormat1;
    DateFormat dateFormat21;
    TimeZone timeZone21;
    String nowtime;
    Date reqDate1;
    long reqDateTime1;
    long curDateTime1;
    long minute;


    //노트업데이트
    public void Notetime(){
        notedate= new Date();
        notesdf = new SimpleDateFormat("yy/MM/dd");
        noetime = notesdf.format(notedate);
    }

    public void koreantime(){
        date2= new Date();
        sdf2 = new SimpleDateFormat("yy-MM-dd a hh:mm");
        newstime2 = sdf2.format(date2);
    }


    public void globaltime(){
        for(int i=0; i<22; i++){
            switch(i){
                case 0:

                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Dubai"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i]);


                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Dubai"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                     reqDateTime1 = reqDate1.getTime();
                     curDateTime1 = curDate1.getTime();
                     minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";

                    break;
                case 1:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Australia/Canberra"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Australia/Canberra"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";

                    break;
                case 2:


                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/com.example.newopenapiexchangeproject3.NationIntent.Bahrain"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );


                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/com.example.newopenapiexchangeproject3.NationIntent.Bahrain"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;

                case 3:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("America/Toronto"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("America/Toronto"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 4:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Zurich"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/Zurich"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 5:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Shanghai"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Shanghai"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 6:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Copenhagen"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/Copenhagen"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 7:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Brussels"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/Brussels"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 8:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/London"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/London"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 9:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Hong_Kong"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Hong_Kong"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 10:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Jakarta"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Jakarta"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 11:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Tokyo"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Tokyo"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 12:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Seoul"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Seoul"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 13:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Kuwait"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Kuwait"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 14:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 15:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Oslo"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/Oslo"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 16:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Pacific/Auckland"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Pacific/Auckland"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 17:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Riyadh"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Riyadh"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 18:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Stockholm"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Europe/Stockholm"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 19:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Singapore"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Singapore"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 20:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Bangkok"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("Asia/Bangkok"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
                case 21:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("America/New_York"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );

                    //맨 앞 시간차이 나타내기
                    curDate1 = new Date();
                    dateFormat1 = new SimpleDateFormat("HHmm");
                    dateFormat21 = new SimpleDateFormat("HHmm"); //그 나라 시간.
                    timeZone21  = TimeZone.getTimeZone("America/New_York"); //그 나라 표준시
                    dateFormat21.setTimeZone(timeZone21);
                    nowtime=dateFormat21.format(curDate1);
                    reqDate1 = null;
                    try {
                        reqDate1 = dateFormat1.parse(nowtime);
                        curDate1 = dateFormat1.parse(dateFormat1.format(curDate1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    reqDateTime1 = reqDate1.getTime();
                    curDateTime1 = curDate1.getTime();
                    minute = (curDateTime1 - reqDateTime1) /3600000;//밀리새컨드로 표시하기에
                    minute=Math.abs(minute);
                    timedifferent[i]=minute+"";
                    break;
            }


        }

    }


}
