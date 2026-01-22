package kr.astar.wfliv.listener;

import kr.astar.wfliv.data.alert.Donation;
import okhttp3.Response;
import org.jspecify.annotations.NonNull;

public interface WeflabListener {
//    default void onChat(Chat chat) {}
    default void onDonation(Donation donation) {}
    default void onConnect(@NonNull Response response) {}
    default void onDisconnect(int code, String reason) {}
    default void onFail(Throwable t, Response response) {}
}
