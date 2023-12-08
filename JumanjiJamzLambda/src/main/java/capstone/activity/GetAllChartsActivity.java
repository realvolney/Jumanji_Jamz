package capstone.activity;

import capstone.activity.requests.GetAllChartsRequest;
import capstone.activity.results.GetAllChartsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetAllChartsActivity {

    private final Logger log = LogManager.getLogger();
    private final ChartDAO dao;
    private final ModelConverter converter = new ModelConverter();

    @Inject
    public GetAllChartsActivity(ChartDAO dao) {
        this.dao = dao;
    }

    public GetAllChartsResult handleRequest(final GetAllChartsRequest request) {
        log.info("Received GetAllChartsRequest {}", request);

        String id = request.getId();

        List<ChartModel> chartList = converter.toChartModelList(dao.getAllCharts(id));

        ChartModel nextStartKey = chartList.get(chartList.size()-1);
        if (nextStartKey != null) {
            id = String.valueOf(nextStartKey.getId());
        }

        return GetAllChartsResult.builder()
                .withId(id)
                .withCharts(chartList)
                .build();
    }
}
