package capstone.activity;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class AddChartActivity {

    private SetListDAO setListDAO;
    private MetricsPublisher publisher;
    private Logger log = LogManager.getLogger();

    @Inject
    public AddChartActivity(SetListDAO setListDAO, MetricsPublisher publisher) {
        this.setListDAO = setListDAO;
        this.publisher = publisher;
    }

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
                .withMadeBY(request.getMadeBy())
                .build();

        SetList setList = setListDAO.getSetList(id);
        setList.getCharts().add(chart);
        setListDAO.saveSetList(setList);

        return AddChartResult.builder()
                .withChart(chart)
                .withId(id)
                .build();
    }
}