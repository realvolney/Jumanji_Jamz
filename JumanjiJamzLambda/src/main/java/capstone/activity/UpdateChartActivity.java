package capstone.activity;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateChartActivity {

    private ChartDAO dao;
    private MetricsPublisher publisher;
    private Logger log = LogManager.getLogger();
    private ModelConverter converter = new ModelConverter();

    /**
     * Instantiates UpdateChartActivity.
     * @param dao ChartDao to access Charts table.
     * @param publisher MetricsPublisher for publishing metrics.
     */
    @Inject
    public UpdateChartActivity(ChartDAO dao, MetricsPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }

    /**
     * HandleRequest method for UpdateChartActivity.
     * @param request UpdateChartRequest to be processed.
     * @return UpdateChartResult with updated chart.
     */
    public UpdateChartResult handleRequest(final UpdateChartRequest request) {
        log.info("Received UpdateChartActivity request", request);

        String name = request.getName();

        // Check for invalid characters in the name
        if (!name.matches("[a-zA-Z0-9 ]*")) {
            publisher.addCount(MetricsConstants.UPDATE_CHART_SUCCESS_COUNT, 0);
            throw new IllegalArgumentException("Chart 'Name' must be alphaNumeric.");
        }
        Chart chart = dao.getChart(request.getId());

        if (!chart.getMadeBy().equals(request.getMadeBy())) {
            log.error(String.format("User: '%s' does not own chart: '%s'",
                    request.getMadeBy(), request.getId()));
            publisher.addCount(MetricsConstants.UPDATE_CHART_SUCCESS_COUNT, 0);
            throw new SecurityException("You must own Chart to update it.");
        }
        if (!request.getName().isBlank()) {
            chart.setName(request.getName());
        }
        if (!request.getArtist().isBlank()) {
            chart.setArtist(request.getArtist());
        }
        if (request.getBpm() != null) {
            chart.setBpm(request.getBpm());
        }
        if (!request.getContent().isBlank()) {
            chart.setContent(request.getContent());
        }
        if (request.getGenres() != null) {
            chart.setGenres(request.getGenres());
        }

        ChartModel model = converter.toChartModel(dao.saveChart(chart));

        publisher.addCount(MetricsConstants.UPDATE_CHART_SUCCESS_COUNT, 1);
        return UpdateChartResult.builder()
                .withChart(model)
                .build();

    }
}
