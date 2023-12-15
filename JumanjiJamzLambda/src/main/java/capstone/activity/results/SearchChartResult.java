package capstone.activity.results;

import capstone.models.ChartModel;

import java.util.List;

import static capstone.utils.CollectionUtils.copyToList;

public class SearchChartResult {
    private final List<ChartModel> charts;

    private SearchChartResult(List<ChartModel> charts) {
        this.charts = charts;
    }

    public List<ChartModel> getCharts() {
        return charts;
    }

    @Override
    public String toString() {
        return "SearchChartResult{" +
                "charts=" + charts +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ChartModel> charts;

        public Builder withCharts(List<ChartModel> charts) {
            this.charts = copyToList(charts);
            return this;
        }

        public SearchChartResult build() {
            return new SearchChartResult(charts);
        }
    }
}
