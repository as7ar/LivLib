package kr.astar.uliv.listener;


import kr.astar.uliv.data.Chat;
import kr.astar.uliv.data.SuperChat;
import kr.astar.uliv.data.SuperSticker;

public interface YouTubeEventListener {
    default void onChat(Chat chat) {}
    default void onSuperChat(SuperChat superChat) {}
    default void onSuperSticker(SuperSticker superSticker) {}
    default void onError(Exception e) {}
}
