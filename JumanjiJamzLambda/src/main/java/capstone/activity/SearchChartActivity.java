package capstone.activity;

import capstone.activity.requests.SearchChartRequest;
import capstone.activity.results.SearchChartResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

import java.util.List;

import static capstone.utils.NullUtils.ifNull;

public class SearchChartActivity {

    private final Logger log = LogManager.getLogger();
    private final ChartDAO dao;

    @Inject
    public SearchChartActivity(ChartDAO dao) {
        this.dao = dao;
    }

    /**
     * This method handles the incoming request by searching for charts from the database.
     * <p>
     * It then returns the matching charts, or an empty result list if none are found.
     *
     * @param request  object containing the search criteria
     * @return SearchChartResult object containing the charts that match the
     * search criteria.
     */
    public SearchChartResult handleRequest(final SearchChartRequest request) {
        log.info("Received SearchChartRequest {}", request);

        String criteria = ifNull(request.getCriteria(), "");
        String[] criteriaArray = criteria.isBlank() ? new String[0] : criteria.split("\\s");

        List<Chart> results = dao.searchCharts(criteriaArray);
        List<ChartModel> chartModels = new ModelConverter().toChartModelList(results);

        return SearchChartResult.builder()
                .withCharts(chartModels)
                .build();
    }
}
