package kr.astar.uliv.exception;

public class NullLiveChatId extends IllegalStateException {
    public NullLiveChatId() {
        super("Live chat ID not found");
    }
}
