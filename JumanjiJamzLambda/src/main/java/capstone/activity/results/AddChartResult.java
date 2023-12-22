package capstone.activity.results;

import capstone.models.ChartModel;

public class AddChartResult {
    public ChartModel chart;
    public String id;

    private AddChartResult(ChartModel chart, String id) {
        this.chart = chart;
        this.id = id;
    }

    public ChartModel getChart() {
        return chart;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AddChartResult{" +
                "chart=" + chart +
                ", id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private ChartModel chart;
        private String id;

        public Builder withChart(ChartModel chart) {
            this.chart = chart;
            return this;
        }

        public Builder withId(String id) {
            this.id =id;
            return this;
        }

        public AddChartResult build() {
            return new AddChartResult(chart, id);
        }
    }
}
