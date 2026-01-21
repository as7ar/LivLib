package kr.astar.wfliv.listener;

import kr.astar.wfliv.data.Chat;
import kr.astar.wfliv.data.Donation;

public interface WeflabListener {
    default void onChat(Chat chat) {}
    default void onDonation(Donation donation) {}
    default void onConnect() {}
    default void onDisconnect() {}
    default void onFail() {}
}
