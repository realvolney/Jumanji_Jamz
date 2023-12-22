package capstone.dynamodb;

import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

/**
 * Accesses data for a chart using {@Link SetList} to represent the model in DynamoDB.
 */
public class SetListDAO {

    private final MetricsPublisher publisher;
    private final DynamoDBMapper mapper;
    private final Logger log = LogManager.getLogger();
    private static final String SETLIST_NAME_GSI = "SetListNameAndMadeByIndex";

    /**
     * Instantiates ChartDao object.
     * @param publisher the {@link DynamoDBMapper} used to interact with the Charts table
     * @param mapper the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public SetListDAO(MetricsPublisher publisher, DynamoDBMapper mapper) {
        this.publisher = publisher;
        this.mapper = mapper;
    }

    /**
     * Creates a setList in the database.
     * @param setList the setList object to be saved
     * @return true if it was successful, false if unsuccessful.
     */
    public boolean createSetList(SetList setList) {
        if (Objects.isNull(setList)) {
            throw new IllegalArgumentException("SetList cannot be null");
        }

        try {
            mapper.save(setList);
            publisher.addCount(MetricsConstants.CREATE_SET_LIST_SUCCESS_COUNT, 1);
            return true;
        } catch (RuntimeException e) {
            log.error("Error creating setList", e);
            publisher.addCount(MetricsConstants.CREATE_SET_LIST_SUCCESS_COUNT, 0);
        }
        return false;
    }

    /**
     * Gets one setList from the database.
     * @param id is the id of the setList to be found
     * @return the setList
     */
    public SetList getSetList(String id) {
        log.info(String.format("looking for chart with id: '%s' ", id));
        SetList setList = mapper.load(SetList.class, id);

        if (setList == null) {
            log.warn("Could not find setList with id: " + id);
            publisher.addCount(MetricsConstants.GET_SET_LIST_SUCCESS_COUNT, 0);
            return setList;
        }
        publisher.addCount(MetricsConstants.GET_SET_LIST_SUCCESS_COUNT, 1);
        return setList;
    }

    /**
     * Saves setList to dataBase.
     * @param setList the Setlist to be saved to Setlists
     * @return SetList that was saved
     */
    public SetList saveSetList(SetList setList) {
        log.info("Saving setList {}", setList);
        try {
            mapper.save(setList);
        } catch (RuntimeException e) {
            log.error("Unable to save setList {}", setList);
            publisher.addCount(MetricsConstants.SAVE_SET_LIST_SUCCESS_COUNT, 0);
            return  setList;
        }
        publisher.addCount(MetricsConstants.SAVE_SET_LIST_SUCCESS_COUNT, 1);
        return setList;
    }

    public List<SetList> getMySetLists(String madeBy) {
        log.info("getting my setlists madeBy: {}", madeBy);

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":madeBy", new AttributeValue().withS(madeBy));

        DynamoDBQueryExpression<SetList> expression = new DynamoDBQueryExpression<SetList>()
                .withIndexName(SETLIST_NAME_GSI)
                .withConsistentRead(false)
                .withExpressionAttributeValues(valueMap);

        return mapper.query(SetList.class, expression);
    }
}
