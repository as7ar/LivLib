package kr.astar.wfliv.data.alert;

public class DonationData {
    private final String page;
    private final String idx;
    private final String pageid;
    private final String preset;

    public DonationData(String page, String idx, String pageid, String preset) {
        this.page = page;
        this.idx = idx;
        this.pageid = pageid;
        this.preset = preset;
    }

    public String page() {
        return page;
    }

    public String idx() {
        return idx;
    }

    public String pageid() {
        return pageid;
    }

    public String preset() {
        return preset;
    }
}