package capstone.activity.results;

;

import capstone.models.ChartModel;


/**
 * This class represents a result to update a chart.
 * It is used as part of the UpdateChartActivity
 */
public class UpdateChartResult {
    private ChartModel chart;

    private UpdateChartResult(ChartModel chart) {
        this.chart = chart;
    }

    public ChartModel getChart() {
        return chart;
    }

    @Override
    public String toString() {
        return "UpdateChartResult{" +
                "chart=" + chart +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChartModel chart;

        public Builder withChart(ChartModel chart) {
            this.chart = chart;
            return this;
        }

        public UpdateChartResult build() {
            return new UpdateChartResult(chart);
        }
    }
}
