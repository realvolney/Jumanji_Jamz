package capstone.dynamodb;

import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
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
    private String FAILURE = MetricsConstants.CREATE_SET_LIST_FAIL_COUNT;

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


    }
}
