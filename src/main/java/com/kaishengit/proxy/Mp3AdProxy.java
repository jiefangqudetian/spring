package com.kaishengit.proxy;

public class Mp3AdProxy implements Player {
    private Player target;
    public Mp3AdProxy(Player player){
        target = player;
    }

    public int play(String username) {

        System.out.println("正在播放广告");
        target.play(username);
        return 1;
    }

    public void stop() {
        System.out.println("即将进入广告");
        target.stop();
    }
}
