package com.kaishengit.proxy;

public class Mp3 implements Player {


    public int play(String username) {
        System.out.println("正在播放："+username);
        //int a = 10/0;
        return 10/1;
    }

    public void stop() {
        System.out.println("即将停止播放");
    }
}
