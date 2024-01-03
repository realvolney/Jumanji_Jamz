package capstone.activity;

import capstone.activity.requests.MySetListsRequest;
import capstone.activity.results.MySetListsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.inject.Inject;

public class MySetListsActivity {

    private final SetListDAO dao;
    private final MetricsPublisher publisher;

    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates MySetListsActivity object.
     * @param dao SetListDAO to access SetLists table
     * @param publisher to publish the metrics to cloudWatch
     */
    @Inject
    public MySetListsActivity(SetListDAO dao, MetricsPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }

    /**
     * Handles request object.
     * @param request MySetListsRequest object with the userId.
     * @return List of SetListModels result object.
     */
    public MySetListsResult handleRequest(final MySetListsRequest request) {
        log.info("Received MySetListRequest {}", request);
        String madeBy = request.getMadeBy();

        List<SetList> setListList = dao.getMySetLists(madeBy);

        return MySetListsResult.builder()
                .withSetLists(new ModelConverter().toSetListModelList(setListList))
                .build();
    }
}
