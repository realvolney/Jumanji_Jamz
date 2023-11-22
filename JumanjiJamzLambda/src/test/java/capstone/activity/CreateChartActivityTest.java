package capstone.activity;

import capstone.activity.requests.CreateChartRequest;
import capstone.activity.results.CreateChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CreateChartActivityTest {
    @InjectMocks
    private CreateChartActivity activity;

    @Mock
    private ChartDAO dao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_successful_returnsResult() {
        // GIVEN
        CreateChartRequest request = CreateChartRequest.builder()
                .withName("name")
                .build();
        when(dao.createChart(any(Chart.class))).thenReturn(true);

        // WHEN
        CreateChartResult result = activity.handleRequest(request);

        // THEN
        assertEquals(result.getName(), request.getName(), "name should be the same");
        assertTrue(result.isSuccess());
    }

    @Test
    void handleRequest_twoRequests_differentIds() {
        // GIVEN
        CreateChartRequest request = CreateChartRequest.builder()
                .withName("name")
                .build();
        when(dao.createChart(any(Chart.class))).thenReturn(true);

        // WHEN
        CreateChartResult result1 = activity.handleRequest(request);
        CreateChartResult result2 = activity.handleRequest(request);

        // THEN
        assertNotEquals(result1.getId(), result2.getId());
    }
}
