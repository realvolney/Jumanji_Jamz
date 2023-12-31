package capstone.activity;

import capstone.activity.requests.CreateChartRequest;
import capstone.activity.results.CreateChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

import javax.inject.Inject;
public class CreateChartActivity {
    private final Logger log = LogManager.getLogger();
    private final ChartDAO dao;

    /**
     * Instantiates CreateChartActivity.
     * @param dao ChartDAO to access Charts table.
     */
    @Inject
    public CreateChartActivity(ChartDAO dao) {
        this.dao = dao;
    }

    /**
     * HandleRequest method.
     * @param request CreateChartRequest with necessary fields to create chart.
     * @return CreateChartResult with a boolean if it was successful, the id and person who made it.
     */
    public CreateChartResult handleRequest(final CreateChartRequest request) {
        log.info("Received CreateChartRequest {}", request);

        String name = request.getName();

        // Check for invalid characters in the name
        if (!name.matches("[a-zA-Z0-9 ]*")) {
            throw new IllegalArgumentException("Invalid characters in the vendor name.");
        }

        UUID id = UUID.randomUUID();

        Chart chart = new Chart();
        chart.setId(String.valueOf(id));
        chart.setName(name);
        chart.setArtist(request.getArtist());
        chart.setBpm(request.getBpm());
        chart.setContent(request.getContent());
        chart.setGenres(request.getGenres());
        chart.setMadeBy(request.getMadeBy());

        boolean success = dao.createChart(chart);

        return CreateChartResult.builder()
                .withId(String.valueOf(id))
                .withName(name)
                .withSuccess(success)
                .build();
    }
}
