package kr.astar.wfliv.data;

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

