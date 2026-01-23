package kr.astar.wfliv.listener;

import kr.astar.wfliv.data.alert.Donation;
import kr.astar.wfliv.data.alert.RouletteResult;
import lombok.NonNull;
import okhttp3.Response;

public interface WeflabListener {
//    default void onChat(Chat chat) {}
    default void onDonation(Donation donation) {}
//    default void onRouletteRoll(RouletteResult result) {}
    default void onConnect(@NonNull Response response) {}
    default void onDisconnect(int code, String reason) {}
    default void onFail(Throwable t, Response response) {}
}
