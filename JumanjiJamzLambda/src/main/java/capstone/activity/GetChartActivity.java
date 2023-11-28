package capstone.activity;

import capstone.activity.requests.GetChartRequest;
import capstone.activity.results.GetChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


/**
 * Implementation of the GetChartActivity for the JumanjiJamz GetChartActivity API
 * this API allows the customer to view one chart.
 */
public class GetChartActivity {
    private final Logger log = LogManager.getLogger();
    private final ChartDAO dao;

    /**
     * Instantiates a GetChartActivity object.
     * @param dao the ChartDAO to access the Charts table.
     */
    @Inject
    public GetChartActivity(ChartDAO dao) {
        this.dao = dao;
    }

    /**
     * Handles incoming request to retrieve one chart by id
     * @param request the request object
     * @return the result objects containing all the chart item attributes.
     */
    public GetChartResult handleRequest(final GetChartRequest request) {
        log.info("Received GerChartRequest {}", request);

        String id = request.getId();

        Chart chart = dao.getChart(id);

        return GetChartResult.builder()
                .withId(String.valueOf(chart.getId()))
                .withName(chart.getName())
                .withArtist(chart.getArtist())
                .withBpm(chart.getBpm())
                .withContent(chart.getContent())
                .withGenres(chart.getGenres())
                .withMadeBy(chart.getMadeBy())
                .build();
    }
}