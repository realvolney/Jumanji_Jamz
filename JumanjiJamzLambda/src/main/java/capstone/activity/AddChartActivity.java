package capstone.activity;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

public class AddChartActivity {

    private SetListDAO setListDAO;
    private MetricsPublisher publisher;
    private Logger log = LogManager.getLogger();

    /**
     * Instantiates AddChartActivity object.
     * @param setListDAO dao to access the setList table
     * @param publisher to publish all metrics
     */
    @Inject
    public AddChartActivity(SetListDAO setListDAO, MetricsPublisher publisher) {
        this.setListDAO = setListDAO;
        this.publisher = publisher;
    }


    /**
     * Handles request.
     * @param request AddChartRequest with containing setListId and chart.
     * @return AddChartResult containing the setListId and the ChartModel
     */
    public AddChartResult handleRequest(final AddChartRequest request) {
        log.info("request {}", request);

        String id = request.getSetListId();

        ChartModel chart = ChartModel.builder()
                .withId(request.getId())
                .withName(request.getName())
                .withArtist(request.getArtist())
                .withBpm(request.getBpm())
                .withContent(request.getContent())
                .withGenres(request.getGenres())
                .withMadeBy(request.getMadeBy())
                .build();
        log.info("chartModel {}", chart);
        SetList setList = setListDAO.getSetList(id);
        log.info("setList {}", setList);
        if (!Objects.equals(request.getUser(), setList.getMadeBy())) {
            throw new IllegalArgumentException("You do not own this setlist");
        }
        Set<ChartModel> charts = setList.getCharts();
        log.info("charts", charts);
        if (charts == null) {
            charts = new HashSet<>();
        }

        charts.add(chart);
        setList.setCharts(charts);

        setListDAO.saveSetList(setList);

        return AddChartResult.builder()
                .withChart(chart)
                .withId(id)
                .build();
    }
}
