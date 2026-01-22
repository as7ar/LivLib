package kr.astar.wfliv.data.streamer;

public record StreamerData(
        String loginType,
        String idx,
        String userid,
        String id, String email,
        boolean isMobile,
        boolean isIOS
) {
}
