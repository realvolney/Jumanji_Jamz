package capstone.dependency;

//CHECKSTYLE:OFF:ServiceComponent
import capstone.activity.*;

import dagger.Component;

import javax.inject.Singleton;


/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

     /**
      * Provides the relevant activity.
      * @return CreateChartActivity
      */
     CreateChartActivity provideCreateCharActivity();

     /**
      * Provides the relevant activity.
      * @return CreateSetListActivity
      */
     CreateSetListActivity provideCreateSetListActivity();

     /**
      * Provides the relevant activity.
      * @return GetChartActivity
      */
     GetChartActivity provideGetChartActivity();

     /**
      * Provides the relevant activity.
      * @return UpdateChartActivity
      */
     UpdateChartActivity provideUpdateChartActivity();

     /**
      * Provides the relevant activity.
      * @return GetSetListActivity
      */
     GetSetListActivity provideGetSetListActivity();

     /**
      * Provides the relevant activity.
      * @return UpdateSetListActivity
      */
     UpdateSetListActivity provideUpdateSetListActivity();

     /**
      * Provides the relevant activity.
      * @return GetAllChartsActivity
      */
     GetAllChartsActivity provideGetAllChartsActivity();

     /**
      * Provides the relevant activity.
      * @return SearchChartActivity
      */
     SearchChartActivity provideSearchChartActivity();

     /**
      * Provides the relevant activity.
      * @return SearchChartActivity
      */
     AddChartActivity provideAddChartActivity();

     /**
      * Provides the relevant activity.
      * @return MySetListActivity
      */
     MySetListsActivity provideMySetListsActivity();
}
