package capstone.activity;

import capstone.activity.requests.UpdateSetListRequest;
import capstone.converters.ModelConverter;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.helper.ChartTestHelper;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import capstone.models.ChartModel;
import capstone.models.SetListModel;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateSetListActivityTest {
    @InjectMocks
    private UpdateSetListActivity activity;

    @Mock
    private SetListDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_returnsResult (){
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());
        Set<ChartModel> expectedCharts = new HashSet<>(ChartTestHelper.generateChartModelList(4));
        SetList setList = new SetList();
        setList.setId(id);
        setList.setName("name");
        setList.setCharts(expectedCharts);
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        UpdateSetListRequest request = UpdateSetListRequest.builder()
                .withId(id)
                .withName("name")
                .withCharts(expectedCharts)
                .withGenres(new HashSet<>(Arrays.asList("Funk", "Soul")))
                .withMadeBy("me")
                .build();
        when(dao.getSetList(id)).thenReturn(setList);
        when(dao.saveSetList(setList)).thenReturn(setList);

        // WHEN
        SetListModel result = activity.handleRequest(request).getSetList();

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertEquals(result.getCharts(), setList.getCharts(), "Charts should be equal");
        assertEquals(result.getGenres(), setList.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "MadeBY should be equal");

        verify(dao, times(1)).getSetList(id);
        verify(dao, times(1)).saveSetList(setList);
        verify(publisher, times(1)).addCount(MetricsConstants.UPDATE_SET_LIST_SUCCESS_COUNT, 1);
    }
}
