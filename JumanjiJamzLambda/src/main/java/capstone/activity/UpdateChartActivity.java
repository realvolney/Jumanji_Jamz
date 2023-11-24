package capstone.activity;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;
import capstone.models.SetListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class UpdateChartActivity {

    private ChartDAO dao;
    private MetricsPublisher publisher;
    private Logger log = LogManager.getLogger();

    /**
     *
     * @param dao
     * @param publisher
     * @param log
     */
    public UpdateChartActivity(ChartDAO dao, MetricsPublisher publisher, Logger log) {
        this.dao = dao;
        this.publisher = publisher;
        this.log = log;
    }

    public UpdateChartResult handleRequest(final UpdateChartRequest request) {
        log.info("Received UpdateChartActivity request", request);

        String name = request.getName();

        // Check for invalid characters in the name
        if (!name.matches("[a-zA-Z0-9 ]*")) {
            throw new IllegalArgumentException("Invalid characters in the vendor name.");
        }


        ChartModel chart = ChartModel.builder()
                .withId(UUID.fromString(request.getId()))
                .withName(request.getName())
                .withArtist(request.getArtist())
                .withBpm(request.getBpm())
                .withContent(request.getContent())
                .withGenres(request.getGenres())
                .withMadeBY(request.getMadeBy())
                .build();

        return UpdateChartResult.builder()
                .withChart(chart)
                .build();

    }
}
