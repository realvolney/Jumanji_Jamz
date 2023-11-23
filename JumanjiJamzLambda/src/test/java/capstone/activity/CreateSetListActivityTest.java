package capstone.activity;

import capstone.activity.requests.CreateSetListRequest;
import capstone.activity.results.CreateSetListResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.SetList;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CreateSetListActivityTest {
    @InjectMocks
    private CreateSetListActivity activity;

    @Mock
    private SetListDAO dao;

    @BeforeEach
    void setUp() {openMocks(this); }

    @Test
    void handleRequest_successful_returnsResult() {
        // GIVEN
        CreateSetListRequest request = CreateSetListRequest.builder()
                .withName("name")
                .build();
        when(dao.createSetList(any(SetList.class))).thenReturn(true);

        // WHEN
        CreateSetListResult result = activity.handleRequest(request);

        // THEN
        assertEquals(result.getName(), request.getName(), "names should be the same");
        assertTrue(result.isSuccess());
        verify(dao, times(1)).createSetList(any(SetList.class));
    }

    @Test
    void handleRequest_twoRequests_differentIds() {
        // GIVEN
        CreateSetListRequest request = CreateSetListRequest.builder()
                .withName("name")
                .build();
        when(dao.createSetList(any(SetList.class))).thenReturn(true);

        // WHEN
        CreateSetListResult result1 = activity.handleRequest(request);
        CreateSetListResult result2 = activity.handleRequest(request);

        // THEN
        assertNotEquals(result1.getId(), result2.getId());
    }
}
