package kr.astar.wfliv.data.alert;

public class PlatformData {
    private final String platform;
    private final String donationtype;

    public PlatformData(String platform, String donationtype) {
        this.platform = platform;
        this.donationtype = donationtype;
    }

    public String platform() {
        return platform;
    }

    public String donationtype() {
        return donationtype;
    }
}