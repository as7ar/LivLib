package kr.astar.wfliv.data.alert;

public class Donation {
    private final User user;
    private final String content;
    private final long amount;
    private final boolean isTest;
    private final long timestamp;
    private final DonationData donationData;
    private final PlatformData platformData;

    public Donation(User user, String content, long amount, boolean isTest, long timestamp, DonationData donationData, PlatformData platformData) {
        this.user = user;
        this.content = content;
        this.amount = amount;
        this.isTest = isTest;
        this.timestamp = timestamp;
        this.donationData = donationData;
        this.platformData = platformData;
    }

    public User user() {
        return user;
    }

    public String content() {
        return content;
    }

    public long amount() {
        return amount;
    }

    public boolean isTest() {
        return isTest;
    }

    public long timestamp() {
        return timestamp;
    }

    public DonationData donationData() {
        return donationData;
    }

    public PlatformData platformData() {
        return platformData;
    }
}