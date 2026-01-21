package kr.astar.tooliv.utilities;

import lombok.Getter;

@Getter
public class Debugger {
    private boolean isDebug=false;
    public Debugger(boolean isDebug) {
        this.isDebug=isDebug;
    }
    public static void debug(String message) {
        System.out.println(message);
    }

}
