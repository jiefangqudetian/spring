package com.kaishengit.proxy;

public class Mp3LogProxy implements Player {
    private Player target;
    public Mp3LogProxy(Player player){
        target = player;
    }

    public int play(String username) {
        System.out.println("开始播放："+username);
        target.play(username);
        return 7;
    }

    public void stop() {
        target.stop();
        System.out.println("停止播放");
    }
}
