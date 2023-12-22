package capstone.activity;

import capstone.dynamodb.SetListDAO;
import capstone.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

        // WHEN

        // THEN
    }
}
