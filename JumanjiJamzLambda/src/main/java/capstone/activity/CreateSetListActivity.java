package capstone.activity;

import capstone.activity.requests.CreateSetListRequest;
import capstone.activity.results.CreateSetListResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

import javax.inject.Inject;


public class CreateSetListActivity {
    private final Logger log = LogManager.getLogger();
    private final SetListDAO dao;

    /**
     * For Instantiating CreateSetListActivity object.
     * @param dao the setList dao for accessing the SetList table.
     */
    @Inject
    public CreateSetListActivity(SetListDAO dao) {
        this.dao = dao; }

    /**
     * method for handling frontend request.
     * @param request CreateSetListRequest for method.
     * @return CreateSetListResult object.
     */

    public CreateSetListResult handleRequest(final CreateSetListRequest request) {
        log.info("Received CreateSetListRequest {}", request);

        String name = request.getName();

        // Check for invalid characters in the name
        if (!name.matches("[a-zA-Z0-9 ]*")) {
            throw new IllegalArgumentException("Invalid characters in the setlist name.");
        }
        UUID id = UUID.randomUUID();

        SetList setList = new SetList();
        setList.setId(String.valueOf(id));
        setList.setName(name);
        setList.setCharts(request.getCharts());
        setList.setGenres(request.getGenres());
        setList.setMadeBy(request.getMadeBy());

        boolean success = dao.createSetList(setList);

        return CreateSetListResult.builder()
                .withId(String.valueOf(id))
                .withName(name)
                .withSuccess(success)
                .build();
    }
}
