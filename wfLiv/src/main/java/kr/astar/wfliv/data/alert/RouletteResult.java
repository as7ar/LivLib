package kr.astar.wfliv.data.alert;

public record RouletteResult(
        String idx,
        String type,
        String platforms,
        String value,
        long percent
) {
}
