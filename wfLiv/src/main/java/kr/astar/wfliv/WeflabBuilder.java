package kr.astar.wfliv;

import kr.astar.wfliv.listener.WeflabListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class WeflabBuilder {
    @Getter
    private final String key;
    @Getter
    private List<WeflabListener> listeners;
    @Getter
    private boolean enableDebug;

    public WeflabBuilder(String key) {
        this.key = key;
        listeners=new ArrayList<>();
    }

    public WeflabBuilder addListener(WeflabListener listener) {
        listeners.add(listener);
        return this;
    }

    public WeflabBuilder setEnableDebug(boolean bool) {
        this.enableDebug = bool;
        return this;
    }

    public Weflab build() {
        return new Weflab(this);
    }
}
