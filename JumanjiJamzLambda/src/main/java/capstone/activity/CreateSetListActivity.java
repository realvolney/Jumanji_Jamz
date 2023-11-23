package capstone.activity;

import capstone.dynamodb.SetListDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class CreateSetListActivity {
    private final Logger log = LogManager.getLogger();
    private final SetListDAO dao;

    /**
     * For Instantiating CreateSetListActivity object
     * @param dao: the setList dao for accessing the SetList table
     */
    @Inject
    public CreateSetListActivity(SetListDAO dao) { this.dao = dao; }


}
