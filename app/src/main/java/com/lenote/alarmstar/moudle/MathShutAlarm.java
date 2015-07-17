package com.lenote.alarmstar.moudle;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by shanerle on 2015/7/15.
 */
public class MathShutAlarm implements Serializable {
    int level;//1，a+b;2,a+b+c;3,a*b+c(2位数);4 (a（2）*b（2）)+c(3位数);5(a（3）*b（2）)+c(4位数)
    int a;
    int b;
    int c;//level>1 以后有用

    public MathShutAlarm(int level){
        this.level=level;
        switch (level){
            case 1:
                a= new Random().nextInt(100);
                b= new Random().nextInt(100);
                break;
            case 2:
                a= new Random().nextInt(100);
                b= new Random().nextInt(100);
                c= new Random().nextInt(100);
                break;
            case 3:
                a= new Random().nextInt(100);
                b= new Random().nextInt(10);
                c= new Random().nextInt(100);
                break;
            case 4:
                a= new Random().nextInt(100);
                b= new Random().nextInt(100);
                c= new Random().nextInt(900);
                break;
            case 5:
                a= new Random().nextInt(500);
                b= new Random().nextInt(100);
                c= new Random().nextInt(3000);
                break;
        }
    }
    public int getValue(){
        switch (level){
            case 1:
                return getResultLevel1();
            case 2:
                return getResultLevel2();
            case 3:
            case 4:
            case 5:
                return getResultLevel3();
            default:
                return 0;
        }
    }
    int getResultLevel1(){
        return a+b;
    }
    int getResultLevel2(){
        return a+b+c;
    }
    int getResultLevel3(){
        return a*b+c;
    }

}
