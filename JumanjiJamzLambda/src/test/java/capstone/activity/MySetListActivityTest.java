package capstone.activity;

import capstone.activity.requests.MySetListsRequest;
import capstone.activity.results.MySetListsResult;
import capstone.converters.ModelConverter;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;
import capstone.helper.SetListTestHelper;
import capstone.metrics.MetricsPublisher;
import capstone.models.SetListModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class MySetListActivityTest {

    @InjectMocks
    private MySetListsActivity activity;

    @Mock
    private SetListDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_validResoponse() {
        // GIVEN
        String madeBy = "me";
        List<SetList> setLists = SetListTestHelper.generateSetListList(10);
        MySetListsRequest request = MySetListsRequest.builder()
                .withMadeBy(madeBy)
                .build();
        when(dao.getMySetLists(madeBy)).thenReturn(setLists);
        // WHEN
        MySetListsResult result = activity.handleRequest(request);
        // THEN
        assertEquals(result.getSetLists(),new ModelConverter()
                .toSetListModelList(setLists));

    }
}
