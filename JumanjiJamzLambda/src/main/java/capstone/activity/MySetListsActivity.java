package capstone.activity;

import capstone.activity.requests.MySetListsRequest;
import capstone.activity.results.MySetListsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class MySetListsActivity {

    private final SetListDAO dao;
    private final MetricsPublisher publisher;

    private final Logger log = LogManager.getLogger();

    @Inject
    public MySetListsActivity(SetListDAO dao, MetricsPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }


    public MySetListsResult handleRequest(final MySetListsRequest request) {
        log.info("Received MySetListRequest {}", request);
        String madeBy = request.getMadeBy();

        List<SetList> setListList = dao.getMySetLists(madeBy);

        return MySetListsResult.builder()
                .withSetLists(new ModelConverter().toSetListModelList(setListList))
                .build();
    }
}
