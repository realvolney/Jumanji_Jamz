package capstone.activity;

import capstone.activity.requests.GetSetListRequest;
import capstone.activity.results.GetSetListResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetSetListActivity for the JumanjiJamz GetSetListActivity API
 * this API allows the user to view one setList.
 */
public class GetSetListActivity {
    private final Logger log = LogManager.getLogger();
    private final SetListDAO dao;

    /**
     * Instantiates a GetSetListActivity object.
     * @param dao is the SetListDAO to access the SetLists table.-
     */
    @Inject
    public GetSetListActivity(SetListDAO dao) {
        this.dao = dao;
    }

    public GetSetListResult handleRequest(final GetSetListRequest request) {
        log.info("Received GetSetListRequest {}", request);

        String id = request.getId();

        SetList setList = dao.getSetList(id);



        return GetSetListResult.builder()
                .withId(id)
                .withName(setList.getName())
                .withCharts(setList.getCharts())
                .withGenres(setList.getGenres())
                .withMadeBy(setList.getMadeBy())
                .build();
    }
}
