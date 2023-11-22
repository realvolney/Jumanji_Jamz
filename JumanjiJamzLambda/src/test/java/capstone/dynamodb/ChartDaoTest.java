package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChartDaoTest {
    @InjectMocks
    private ChartDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @Mock
    private DynamoDBMapper mapper;

    private String SUCCESS = MetricsConstants.CREATE_CHART_SUCCESS_COUNT;
    private String FAILURE = MetricsConstants.CREATE_CHART_FAIL_COUNT;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createChart_ChartSaved_returnsTrue() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Chart chart = new Chart();
        chart.setId(id);
        doNothing().when(mapper).save(chart);
        doNothing().when(publisher).addCount(SUCCESS, 1);

        // WHEN
        boolean result = dao.createChart(chart);

        // THEN
        assertTrue(result, "Should have successfully saved chart");
        verify(mapper, times(1)).save(chart);
        verify(publisher, times(1)).addCount(SUCCESS, 1);
    }

    @Test
    void createChart_saveUnsuccessful_returnsFalse() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Chart chart = new Chart();
        chart.setId(id);
        doThrow(RuntimeException.class).when(mapper).save(chart);
        doNothing().when(publisher).addCount(FAILURE, 1);

        // WHEN
        boolean result = dao.createChart(chart);

        // THEN
        assertFalse(result, "Should have failed to save chart");
        verify(mapper, times(1)).save(chart);
        verify(publisher, times(1)).addCount(FAILURE, 1);
    }


}
