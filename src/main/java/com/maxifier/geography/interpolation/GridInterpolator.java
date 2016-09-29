package com.maxifier.geography.interpolation;

import com.maxifier.geography.interpolation.model.Grid;

public interface GridInterpolator {
    Grid interpolate(Grid grid) throws InterruptedException;
}
