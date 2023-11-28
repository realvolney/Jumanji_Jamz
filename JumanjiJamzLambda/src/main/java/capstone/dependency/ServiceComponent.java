package capstone.dependency;

import capstone.activity.CreateChartActivity;
import capstone.activity.CreateSetListActivity;
import capstone.activity.GetChartActivity;
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

}
