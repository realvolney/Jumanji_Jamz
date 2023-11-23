package capstone.dynamodb;

import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Accesses data for a chart using {@Link SetList} to represent the model in DynamoDB
 */
public class SetListDAO {

    private final MetricsPublisher publisher;
    private final DynamoDBMapper mapper;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates ChartDao object
     * @param publisher the {@link DynamoDBMapper} used to interact with the Charts table
     * @param mapper the {@link MetricsPublisher} used to record metrics.
     */
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
            publisher.addCount(MetricsConstants.CREATE_SET_LIST_FAIL_COUNT, 1);
        }
        return false;
    }
}
