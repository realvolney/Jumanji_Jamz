package capstone.activity;

import capstone.activity.requests.GetSetListRequest;
import capstone.activity.results.GetSetListResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetSetListActivityTest {
    @InjectMocks
    private GetSetListActivity activity;

    @Mock
    private SetListDAO dao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_returnsResult() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());
        GetSetListRequest request = GetSetListRequest.builder()
                .withId(id)
                .build();

        SetList setList = new SetList();
        setList.setId(UUID.fromString(id));
        setList.setName("name");
        setList.setCharts(new HashSet<>(Arrays.asList("New", "Sland")));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        when(dao.getSetList(id)).thenReturn(setList);

        // WHEN
        GetSetListResult result = activity.handleRequest(request);

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertEquals(result.getCharts(), setList.getCharts(), "Charts should be equal");
        assertEquals(result.getGenres(), setList.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "MadeBY should be equal");

        verify(dao, times(1)).getSetList(id);

    }
}
