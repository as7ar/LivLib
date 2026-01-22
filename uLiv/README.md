```java
ULiv youtube = new YouTubeBuilder()
                .setApiKey("API-KEY")
                .setVideoId("Live-Video-Id") //https://www.youtube.com/watch?v=(here)
                .addListener(new YouTubeEventListener() {
                    @Override
                    public void onChat(Chatting chat) {
                        System.out.println(chat.author().getDisplayName()+": "+chat.getMessage());
                    }
                }).build();
        
        YouTubeInfo info = youtube.channelInfo();
        System.out.println(info.getChannelName()); // Get Channel Name
        System.out.println(info.getSubscriptionCount()); // Get Channel Subscription Count
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        youtube.stop(); // Stop
```