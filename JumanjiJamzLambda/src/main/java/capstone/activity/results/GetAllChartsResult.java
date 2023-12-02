package capstone.activity.results;

import capstone.models.ChartModel;

import java.util.List;

public class GetAllChartsResult {
    private final String id;
    private final List<ChartModel> charts;

    private GetAllChartsResult(String id, List<ChartModel> charts) {
        this.id = id;
        this.charts = charts;
    }

    public String getId() {
        return id;
    }

    public List<ChartModel> getCharts() {
        return charts;
    }

    @Override
    public String toString() {
        return "GetAllChartsResult{" +
                "id='" + id + '\'' +
                ", charts=" + charts +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String id;
        private List<ChartModel> charts;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCharts(List<ChartModel> charts) {
            this.charts = charts;
            return this;
        }


    }
}
