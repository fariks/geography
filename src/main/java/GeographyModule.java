import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import interpolation.GridInterpolator;
import interpolation.NearestNeighborsGridInterpolator;

/**
 * Created by alsm0813 on 27.09.2016.
 */
public class GeographyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GridInterpolator.class).to(NearestNeighborsGridInterpolator.class).in(Scopes.SINGLETON);
        bind(GridCSVHelper.class).in(Scopes.SINGLETON);
        bind(GridRenderer.class).in(Scopes.SINGLETON);
    }
}
