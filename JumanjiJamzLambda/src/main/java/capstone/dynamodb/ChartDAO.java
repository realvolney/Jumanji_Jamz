package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a chart using {@Link Chart} to represent the model in DynamoDB.
 */
@Singleton
public class ChartDAO {


    private final DynamoDBMapper mapper;
    private final Logger log = LogManager.getLogger();
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates ChartDao object.
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
     * Gets one chart from the database.
     * @param id is the id of the chart to be found
     * @return the chart
     */

    public Chart getChart(String id) {
        log.info(String.format("looking for chart with id: '%s' ", id));
        Chart chart = mapper.load(Chart.class, id);

        if (chart == null) {
            log.warn("Could not find setList with id: " + id);
            metricsPublisher.addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 0);
            return null;
        }
        metricsPublisher.addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 1);
        return chart;
    }

    /**
     * Saves chart to the dataBase.
     * @param chart the Chart to be saved on Charts
     * @return Chart that was saved
     */
    public Chart saveChart(Chart chart) {
        log.info("Saving chart: {}", chart);
        try {
            mapper.save(chart);
        } catch (RuntimeException e) {
            log.error("Unable to save chart {}", chart);
            metricsPublisher.addCount(MetricsConstants.SAVE_CHART_SUCCESS_COUNT, 0);
            return chart;
        }
        metricsPublisher.addCount(MetricsConstants.SAVE_CHART_SUCCESS_COUNT, 1);
        return chart;
    }

    /**
     * Gets a list of charts for chart  table.
     * @param id exclusiveKey to start the paginated list
     * @param limit the pagination limit of the List returned
     * @return List of charts
     */
    public List<Chart> getAllCharts(String id, int limit) {
        Map<String, AttributeValue> valueMap = null;

        if (id != null && !id.isBlank()) {
            valueMap = new HashMap<>();
            valueMap.put("id", new AttributeValue().withS(id));
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withExclusiveStartKey(valueMap)
                .withLimit(limit);

        ScanResultPage<Chart> resultPage = mapper.scanPage(Chart.class, scanExpression);

        if (resultPage == null) {
            metricsPublisher.addCount(MetricsConstants.GET_ALL_CHARTS_SUCCESS_COUNT, 0);
            return null;
        }
        metricsPublisher.addCount(MetricsConstants.GET_ALL_CHARTS_SUCCESS_COUNT, 1);
        return resultPage.getResults();
    }

    /**
     * Perform a search (via a "scan") of the charts table for charts matching the given criteria.
     *
     * Both "name" and "genres" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the name or the genres (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param c an array of String containing search criteria.
     * @return a List of Playlist objects that match the search criteria.
     */
    public List<Chart> searchCharts(String[] c) {
        DynamoDBScanExpression d = new DynamoDBScanExpression();
        if (c.length > 0) {
            Map<String, AttributeValue> v = new HashMap<>();
            Map<String, String> n = new HashMap<>();
            String p = ":c";
            StringBuilder nf = new StringBuilder();
            for (int i = 0; i < c.length; i++) {
                v.put(p + i, new AttributeValue().withS(c[i]));
                n.put("#n" + i, "name");
                nf.append(filterPart("#n", p, i));
            }
            d.setExpressionAttributeValues(v);
            d.setExpressionAttributeNames(n);
            d.setFilterExpression("(" + nf + ")");
        }
        List<Chart> r = this.mapper.scan(Chart.class, d);
        metricsPublisher.addCount(MetricsConstants.SEARCH_CHARTS__SUCCESS_COUNT, 1);
        return r;
    }
    private StringBuilder filterPart(String t, String p, int i) {
        String a = i == 0 ? "" : "and ";
        return new StringBuilder().append(a).append("contains(")
                .append(t).append(i).append(", ")
                .append(p).append(i).append(") ");
    }

}

