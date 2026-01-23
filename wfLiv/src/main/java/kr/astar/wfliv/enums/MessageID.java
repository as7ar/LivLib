package kr.astar.wfliv.enums;

import lombok.Getter;

@Getter
public enum MessageID {
    PING("2"),
    REGISTER("40"),
    MESSAGE("42")
    ;

    private final String number;

    MessageID(String number) {
        this.number = number;
    }

}
