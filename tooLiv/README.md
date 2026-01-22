```java
new ToonationBuilder()
    .setKey("") // https://toon.at/widget/alertbox/(here)
    .addListener(new ToonationEventListener() {
        @Override
        public void onDonation(Donation donation) {
            System.out.println(donation.getNickName()+":"+donation.getAmount());
        }
        
        @Override
        public void onFail() {
            System.out.println("error");
        }
    }).build();
```