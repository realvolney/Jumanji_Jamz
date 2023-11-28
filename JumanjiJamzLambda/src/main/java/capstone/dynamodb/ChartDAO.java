package capstone.dynamodb;

import capstone.converters.ModelConverter;
import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;
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
    private final ModelConverter converter = new ModelConverter();

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
     * @param chart the chart object to be saved
     * @return true if it was successful, false if unsuccessful.
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
            metricsPublisher.addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 0);
            return false;
        }
    }

    /**
     * Gets one chart form the database
     * @param id is the id of the String to be found
     */

    public ChartModel getChart(String id) {
        log.info(String.format("looking for chart with id: '%s' ", id));
        Chart chart = mapper.load(Chart.class, id);

        if (chart == null) {
            metricsPublisher.addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 0);
            throw new IllegalArgumentException(
                    String.format("Chart with id: '%s' could not be found", id));
        }
        metricsPublisher.addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 1);
        return converter.toChartModel(chart);
    }
}
