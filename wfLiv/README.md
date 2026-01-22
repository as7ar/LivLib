```java
import kr.astar.wfliv.Weflab;
import kr.astar.wfliv.WeflabBuilder;
import kr.astar.wfliv.data.alert.Donation;
import kr.astar.wfliv.listener.WeflabListener;

Weflab weflab= new WeflabBuilder("hN_G2suam2VxaA") // https://weflab.com/page/(here)
                    .addListener(new WeflabListener() {
                        @Override
                        public void onDonation(Donation donation) {
                            System.out.println(
                                 donation.user().nickname()+": " + donation.amount()
                            );
                        }

                        @Override
                        public void onDisconnect(int code, String reason) {
                            System.out.println("closed");
                        }
                    })
                    .build();
        System.out.println(weflab.getStreamerName());   
        weflab.close();    
```