package capstone.activity;

import capstone.dynamodb.SetListDAO;

import javax.inject.Inject;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CreateSetListActivity {
    private final Logger log = LogManager.getLogManager();
    private final SetListDAO dao;

    @Inject
    public CreateSetListActivity(SetListDAO dao) { this.dao = dao; }

    
}
