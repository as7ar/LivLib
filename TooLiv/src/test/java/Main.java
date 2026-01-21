import kr.astar.tooliv.Toonation;
import kr.astar.tooliv.ToonationBuilder;
import kr.astar.tooliv.data.Donation;
import kr.astar.tooliv.listener.ToonationEventListener;

public class Main {
    public static void main(String[] args) {
        Toonation toonation = new ToonationBuilder()
                .setKey("567c401744b0dea791fb2a14f6be51ed")
                .addListener(new ToonationEventListener() {
                    @Override
                    public void onDonation(Donation donation) {
                        System.out.println(donation.getComment());
                    }
                })
                .build();
    }
}
