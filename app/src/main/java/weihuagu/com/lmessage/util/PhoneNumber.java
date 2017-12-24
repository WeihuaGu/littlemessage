/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.lmessage.util;

/**
 * Created by root on 17-12-24.
 */

import java.util.Random;
public class PhoneNumber {


    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    private static String getNum(String head){
        String number = head;//定义电话号码以139开头
        Random random = new Random();//定义random，产生随机数
        for (int j = 0; j < 8; j++) {
            //生成0~9 随机数
            number += random.nextInt(9);
        }
        return number;
    }
    public static String getPhonenum(){
        int length=telFirst.length;
        int index = (int) (Math.random() * length);
        return getNum(telFirst[index]);

    }
}
