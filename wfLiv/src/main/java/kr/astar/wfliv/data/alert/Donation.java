package kr.astar.wfliv.data.alert;

public record Donation(
        User user,
        String content,
        long amount,
        boolean isTest,
        long timestamp,
        DonationData donationData,
        PlatformData platformData
) {
}

