package com.wechat.message.response;

/**
 * @author 李智
 * @date 2016/10/29
 *
 * 音乐消息
 */
public class MusicMessage extends BaseMessage{
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
