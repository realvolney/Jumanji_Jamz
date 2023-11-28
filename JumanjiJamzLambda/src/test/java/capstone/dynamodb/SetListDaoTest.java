package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SetListDaoTest {

    @InjectMocks
    private SetListDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @Mock
    private DynamoDBMapper mapper;

    private String SUCCESS = MetricsConstants.CREATE_SET_LIST_SUCCESS_COUNT;

    @BeforeEach
    void setUp() { openMocks(this); }

    @Test
    void createSetList_SetListSaved_returnsTrue() {
        // GIVEN
        UUID id = UUID.randomUUID();
        SetList setList = new SetList();
        setList.setId(id);
        doNothing().when(mapper).save(setList);
        doNothing().when(publisher).addCount(SUCCESS, 1);

        //WHEN
        boolean result = dao.createSetList(setList);

        // THEN
        assertTrue(result, "Should have return true");
        verify(mapper, times(1)).save(setList);
        verify(publisher, times(1)).addCount(SUCCESS, 1);
    }

    @Test
    void createSetList_SetListSaved_returnsFalse() {
        // GIVEN
        UUID id = UUID.randomUUID();
        SetList setList = new SetList();
        setList.setId(id);
        doThrow(RuntimeException.class).when(mapper).save(setList);
        doNothing().when(publisher).addCount(SUCCESS, 1);

        //WHEN
        boolean result = dao.createSetList(setList);

        // THEN
        assertFalse(result, "Should have return false");
        verify(mapper, times(1)).save(setList);
        verify(publisher, times(1)).addCount(SUCCESS, 0);
    }

    @Test
    void getChart_chartExists_returnsChart() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        SetList setList = new SetList();
        setList.setId(UUID.fromString(id));
        setList.setName("name");
        setList.setCharts(new HashSet<>(Arrays.asList("New", "Sland")));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        when(mapper.load(SetList.class, id)).thenReturn(setList);

        // WHEN
        SetList result = dao.getSetList(id);

        // THEN
        assertEquals(result.getId(), UUID.fromString(id), "Ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");

        assertEquals(result.getCharts(), setList.getCharts(), "Charts should be equal");
        assertEquals(result.getGenres(), setList.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).load(SetList.class, id);
        verify(publisher, (times(1))).addCount(MetricsConstants.GET_SET_LIST_SUCCESS_COUNT, 1);
    }

    @Test
    void getSetList_SetListDoesNotExists_returnsNull() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        when(mapper.load(SetList.class, id)).thenReturn(null);

        // WHEN
        SetList result = dao.getSetList(id);

        // THEN
        assertNull(result, "should return null");
        verify(mapper, times(1)).load(SetList.class, id);
        verify(publisher, (times(1))).addCount(MetricsConstants.GET_SET_LIST_SUCCESS_COUNT, 0);
    }
}
