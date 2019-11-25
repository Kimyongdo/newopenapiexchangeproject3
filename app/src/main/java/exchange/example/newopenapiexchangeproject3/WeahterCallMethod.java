package exchange.example.newopenapiexchangeproject3;

public class WeahterCallMethod {

   public void WeahterCallMethod(){
       new Thread(){
           @Override
           public void run() {
               super.run();
               WeatherJSon.WatherSendRequest(292223,0);
               WeatherJSon.WatherSendRequest(2172517,1);
               WeatherJSon.WatherSendRequest(290291,2);
               WeatherJSon.WatherSendRequest(6087824,3);
               WeatherJSon.WatherSendRequest(4899170,4);
               WeatherJSon.WatherSendRequest(1796236,5);
               WeatherJSon.WatherSendRequest(2618425,6);
               WeatherJSon.WatherSendRequest(3337389,7);
               WeatherJSon.WatherSendRequest(2643741,8);
               WeatherJSon.WatherSendRequest(1819729,9);
               WeatherJSon.WatherSendRequest(1642907,10);
               WeatherJSon.WatherSendRequest(1850147,11);
               WeatherJSon.WatherSendRequest(1835848,12);
               WeatherJSon.WatherSendRequest(285570,13);
               WeatherJSon.WatherSendRequest(1735161,14);
               WeatherJSon.WatherSendRequest(6453366,15);
               WeatherJSon.WatherSendRequest(2193732,16);
               WeatherJSon.WatherSendRequest(108410,17);
               WeatherJSon.WatherSendRequest(2673730,18);
               WeatherJSon.WatherSendRequest(1880252,19);
               WeatherJSon.WatherSendRequest(1609350,20);
               WeatherJSon.WatherSendRequest(5128594,21);
           }
       }.start();

   }
}
