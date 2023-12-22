package capstone.activity;

import capstone.activity.requests.MySetListsRequest;
import capstone.activity.results.MySetListResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySetListsActivity {

    private final SetListDAO dao;
    private final MetricsPublisher publisher;

    private final Logger log = LogManager.getLogger();

    public MySetListsActivity(SetListDAO dao, MetricsPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }

    public MySetListResult handleRequest(final MySetListsRequest request) {
        log.info("Received MySetListRequest {}", request);
        return null;
    }
}
