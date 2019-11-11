package com.example.newopenapiexchangeproject3;

import static com.example.newopenapiexchangeproject3.WeatherJSon.WatherSendRequest;

public class WeahterCallMethod {

   public void WeahterCallMethod(){
       new Thread(){
           @Override
           public void run() {
               super.run();
               WatherSendRequest(292223,0);
               WatherSendRequest(2172517,1);
               WatherSendRequest(290291,2);
               WatherSendRequest(6087824,3);
               WatherSendRequest(4899170,4);
               WatherSendRequest(1796236,5);
               WatherSendRequest(2618425,6);
               WatherSendRequest(3337389,7);
               WatherSendRequest(2643741,8);
               WatherSendRequest(1819729,9);
               WatherSendRequest(1642907,10);
               WatherSendRequest(1850147,11);
               WatherSendRequest(1835848,12);
               WatherSendRequest(285570,13);
               WatherSendRequest(1735161,14);
               WatherSendRequest(6453366,15);
               WatherSendRequest(2193732,16);
               WatherSendRequest(108410,17);
               WatherSendRequest(2673730,18);
               WatherSendRequest(1880252,19);
               WatherSendRequest(1609350,20);
               WatherSendRequest(5128594,21);
           }
       }.start();

   }
}
