package kr.astar.wfliv.enums;

import lombok.Getter;

@Getter
public enum DonationType {
    TEST("test_donation"),
    LOAD("load")
    ;

    private final String type;

    DonationType(String type) {
        this.type = type;
    }
}
