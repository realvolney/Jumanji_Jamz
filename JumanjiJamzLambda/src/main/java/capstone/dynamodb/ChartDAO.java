package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * Accesses data for a chart using {@Link Chart} to represent the model in DynamoDB
 */

@Singleton
public class ChartDAO {

    private final MetricsPublisher metricsPublisher;
    private final DynamoDBMapper mapper;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates ChartDao object
     * @param metricsPublisher the {@link DynamoDBMapper} used to interact with the Charts table
     * @param mapper the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public ChartDAO(MetricsPublisher metricsPublisher, DynamoDBMapper mapper) {
        this.metricsPublisher = metricsPublisher;
        this.mapper = mapper;
    }

    /**
     * Creates a chart in the database.
     * @param chart
     * @return
     */
    public boolean createChart(Chart chart) {
        if (Objects.isNull(chart)) {
            throw new IllegalArgumentException("Chart cannot be null");
        }

        try {
            mapper.save(chart);
            metricsPublisher.addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 1);
            return true;
        } catch (RuntimeException e) {
            log.error("Error creating chart", e);
            metricsPublisher.addCount(MetricsConstants.CREATE_CHART_FAIL_COUNT, 1);
            return false;
        }
    }
}
