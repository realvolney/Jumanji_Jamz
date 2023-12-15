package capstone.activity;

import capstone.activity.requests.GetAllChartsRequest;
import capstone.activity.results.GetAllChartsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.models.ChartModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;


public class GetAllChartsActivity {

    private final Logger log = LogManager.getLogger();
    private final ChartDAO dao;
    private final ModelConverter converter = new ModelConverter();

    /**
     * Instantiates GetAllChartsActivity.
     * @param dao ChartDAO to access Charts table
     */
    @Inject
    public GetAllChartsActivity(ChartDAO dao) {
        this.dao = dao;
    }

    /**
     * Handles Request.
     * @param request GetALlChartsRequest with starting key.
     * @return GetAllChartResult with a list of charts.
     */
    public GetAllChartsResult handleRequest(final GetAllChartsRequest request) {
        log.info("Received GetAllChartsRequest {}", request);

        String id = request.getId();

        List<ChartModel> chartList = converter.toChartModelList(dao.getAllCharts(id));

        ChartModel nextStartKey = chartList.get(chartList.size() - 1);
        if (nextStartKey != null) {
            id = String.valueOf(nextStartKey.getId());
        }

        return GetAllChartsResult.builder()
                .withId(id)
                .withCharts(chartList)
                .build();
    }
}
