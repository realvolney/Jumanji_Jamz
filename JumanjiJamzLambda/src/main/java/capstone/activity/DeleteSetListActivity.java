package capstone.activity;

import capstone.activity.requests.DeleteSetListRequest;
import capstone.activity.results.DeleteSetListResult;
import capstone.dynamodb.SetListDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteSetListActivity {
    private SetListDAO dao;
    private final Logger log = LogManager.getLogger();

    @Inject
    public DeleteSetListActivity(SetListDAO dao) {
        this.dao = dao;
    }

    public DeleteSetListResult handleRequest(final DeleteSetListRequest request) {
        log.info("received DeleteSetListRequest {}", request);


        boolean success = dao.deleteSetList(request.getId(), request.getMadeBy());
        String message = success ? "Successful Delete" : "You do not own this entry";

        return DeleteSetListResult.builder()
                .withSuccess(success)
                .withMessage(message)
                .build();
    }
}
