package capstone.activity;

import capstone.activity.requests.UpdateSetListRequest;
import capstone.activity.results.UpdateSetListResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateSetListActivity {

    private SetListDAO dao;
    private MetricsPublisher publisher;
    private Logger log = LogManager.getLogger();
    private ModelConverter converter = new ModelConverter();

    /**
     *  Instantiates UpdateSetListActivity.
     * @param dao SetListDAO to access SetLists table.
     * @param publisher MetricsPublisher to publish metrics to cloudwatch.
     */
    @Inject
    public UpdateSetListActivity(SetListDAO dao, MetricsPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }

    /**
     * Handles request method.
     * @param request UpdateSetListRequest with update field.
     * @return UpdateSetListResult with the updated setList.
     */
    public UpdateSetListResult handleRequest(final UpdateSetListRequest request) {
        log.info("Received UpdateSetListRequest {}", request);

        String name = request.getName();

        // Check for invalid characters in the name
        if (!name.matches("[a-zA-Z0-9 ]*")) {
            publisher.addCount(MetricsConstants.UPDATE_SET_LIST_SUCCESS_COUNT, 0);
            throw new IllegalArgumentException("SetList 'Name' must be alphaNumeric.");
        }

        SetList setList = dao.getSetList(request.getId());

        if (!setList.getMadeBy().equals(request.getMadeBy())) {
            log.error(String.format("User: '%s' does not own setList: '%s'",
                    request.getMadeBy(), request.getId()));
            publisher.addCount(MetricsConstants.UPDATE_SET_LIST_SUCCESS_COUNT, 0);
            throw new SecurityException("You must own SetList to update it.");
        }

        setList.setName(name);
        setList.setCharts(request.getCharts());
        setList.setGenres(request.getGenres());
        setList.setMadeBy(request.getMadeBy());
        setList = dao.saveSetList(setList);

        publisher.addCount(MetricsConstants.UPDATE_SET_LIST_SUCCESS_COUNT, 1);
        return UpdateSetListResult.builder()
                .withSetList(converter.toSetListModel(setList))
                .build();
    }
}
