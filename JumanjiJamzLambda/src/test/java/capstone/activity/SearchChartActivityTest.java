package capstone.activity;

import capstone.activity.requests.SearchChartRequest;
import capstone.activity.results.SearchChartResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.helper.ChartTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SearchChartActivityTest {
    @InjectMocks
    private SearchChartActivity activity;

    @Mock
    ChartDAO dao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_returnsResponse() {
        // GIVEN
        List<Chart> chartList = ChartTestHelper.generateChartList(4);
        String criteria = "govenor";
        SearchChartRequest request = SearchChartRequest.builder()
                .withCriteria(criteria)
                .build();

        when(dao.searchCharts(any(String[].class))).thenReturn(chartList);
        //WHEN
        SearchChartResult result = activity.handleRequest(request);

        //THEN
        assertEquals(result.getCharts(), new ModelConverter().toChartModelList(chartList));
        verify(dao, times(1)).searchCharts(any(String[].class));
    }
}
