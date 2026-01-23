import kr.astar.wfliv.Weflab;
import kr.astar.wfliv.WeflabBuilder;
import kr.astar.wfliv.data.alert.Donation;
import kr.astar.wfliv.listener.WeflabListener;

public class Main {
    public static void main(String[] args) {
        Weflab weflab = new WeflabBuilder("guPK2ODAm2ltaXE")
                .setEnableDebug(true)
                .addListener(new WeflabListener() {
                    @Override
                    public void onDonation(Donation donation) {
                        System.out.println(donation.user().nickname()+": "+donation.content() + " ( "+donation.amount());
                    }
                }).build();
        System.out.println("Connected: " + weflab.getStreamerData().id());
        System.out.println(weflab.getIdx());
    }
}
