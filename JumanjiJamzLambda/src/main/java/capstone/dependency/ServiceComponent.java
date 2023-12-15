package capstone.dependency;

import capstone.activity.CreateChartActivity;
import capstone.activity.CreateSetListActivity;
import capstone.activity.GetAllChartsActivity;
import capstone.activity.GetChartActivity;
import capstone.activity.GetSetListActivity;
import capstone.activity.UpdateChartActivity;
import capstone.activity.UpdateSetListActivity;
import dagger.Component;

import javax.inject.Singleton;

//CHECKSTYLE:OFF:ServiceComponent
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
}
