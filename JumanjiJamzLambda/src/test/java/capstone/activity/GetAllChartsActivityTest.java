package capstone.activity;

import capstone.activity.requests.GetAllChartsRequest;
import capstone.activity.results.GetAllChartsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.helper.ChartTestHelper;
import capstone.models.ChartModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllChartsActivityTest {

    @InjectMocks
    private GetAllChartsActivity activity;

    @Mock
    private ChartDAO dao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_returnsResponse() {
        // GIVEN
        String id = "id";
        GetAllChartsRequest request = GetAllChartsRequest.builder()
                .withId(id)
                .build();
        List<Chart> chartList = ChartTestHelper.generateChartList(4);

        when(dao.getAllCharts(id)).thenReturn(chartList);
        // WHEN
        GetAllChartsResult result = activity.handleRequest(request);

        //THEN
        assertEquals(result.getCharts(),new ModelConverter()
                .toChartModelList(chartList), "Should contain the same charts");
    }
}
