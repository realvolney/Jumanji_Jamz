package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.helper.ChartTestHelper;
import capstone.helper.SetListTestHelper;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

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
        setList.setId(String.valueOf(id));
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
        setList.setId(String.valueOf(id));
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
    void getSetList_setListExists_returnsSetList() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        SetList setList = new SetList();
        setList.setId(String.valueOf(UUID.fromString(id)));
        setList.setName("name");
        setList.setCharts(new HashSet<>(ChartTestHelper.generateChartModelList(4)));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        when(mapper.load(SetList.class, id)).thenReturn(setList);

        // WHEN
        SetList result = dao.getSetList(id);

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
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

    @Test
    void saveSetList_validSave_returnsSetList() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        SetList setList = new SetList();
        setList.setId(String.valueOf(UUID.fromString(id)));
        setList.setName("name");
        setList.setCharts(new HashSet<>(ChartTestHelper.generateChartModelList(4)));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");
        doNothing().when(mapper).save(setList);

        // WHEN
        SetList result = dao.saveSetList(setList);

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertEquals(result.getCharts(), setList.getCharts(), "Charts should be equal");
        assertEquals(result.getGenres(), setList.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).save(setList);
        verify(publisher, (times(1))).addCount(MetricsConstants.SAVE_SET_LIST_SUCCESS_COUNT, 1);
    }

    @Test
    void saveSetList_errorSaving_returnsSetList() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        SetList setList = new SetList();
        setList.setId(String.valueOf(UUID.fromString(id)));
        setList.setName("name");
        setList.setCharts(new HashSet<>(ChartTestHelper.generateChartModelList(4)));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        doThrow(RuntimeException.class).when(mapper).save(setList);

        // WHEN
        SetList result = dao.saveSetList(setList);

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertEquals(result.getCharts(), setList.getCharts(), "Charts should be equal");
        assertEquals(result.getGenres(), setList.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).save(setList);
        verify(publisher, (times(1))).addCount(MetricsConstants.SAVE_SET_LIST_SUCCESS_COUNT, 0);
    }

    @Test
    void getMySetLists_SetListDoesExists_returnsList() {
        // GIVEN
        String madeBy = "madeBy";
        ArgumentCaptor<DynamoDBQueryExpression> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        PaginatedQueryList list = Mockito.mock(PaginatedQueryList.class);
        list.add(SetListTestHelper.generateSetList(1));
        list.add(SetListTestHelper.generateSetList(2));
        list.add(SetListTestHelper.generateSetList(3));

        when(mapper.query(eq(SetList.class), captor.capture())).thenReturn(list);

        // WHEN
        List<SetList> result = dao.getMySetLists(madeBy);

        // THEN
        assertEquals(result, list);
        verify(mapper, times(1)).query(eq(SetList.class), captor.capture());

    }

    @Test
    void getMySetLists_SetListDoesNotExists_returnsEmptyList() {
        // GIVEN
        String madeBy = "madeBy";
        ArgumentCaptor<DynamoDBQueryExpression> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        PaginatedQueryList list = Mockito.mock(PaginatedQueryList.class);
        when(mapper.query(eq(SetList.class), captor.capture())).thenReturn(list);

        // WHEN
        List<SetList> result = dao.getMySetLists(madeBy);

        // THEN
        assertEquals(result, list);
        verify(mapper, times(1)).query(eq(SetList.class), captor.capture());

    }
}
