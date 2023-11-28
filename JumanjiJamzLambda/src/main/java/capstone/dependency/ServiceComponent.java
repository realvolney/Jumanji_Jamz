package capstone.dependency;

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

}
