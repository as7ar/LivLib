package kr.astar.tooliv.listener;

import kr.astar.tooliv.data.Donation;

public interface ToonationEventListener {
//    default void onChat(Chatting chatting) {}
    default void onDonation(Donation donation) {}
    default void onConnect() {}
    default void onDisconnect() {}
    default void onFail() {}
}
