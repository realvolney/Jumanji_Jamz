package capstone.activity;

import capstone.activity.requests.GetChartRequest;
import capstone.activity.results.GetChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;


/**
 * Implementation of the GetChartActivity for the JumanjiJamz GetChartActivity API
 * this API allows the user to view one chart.
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
        log.info("Received GetChartRequest {}", request);

        String id = request.getId();

        Chart chart = dao.getChart(id);
        log.info("Received id {}", id);


        return GetChartResult.builder()
                .withId(id)
                .withName(chart.getName())
                .withArtist(chart.getArtist())
                .withBpm(chart.getBpm())
                .withContent(chart.getContent())
                .withGenres(chart.getGenres())
                .withMadeBy(chart.getMadeBy())
                .build();
    }
}
