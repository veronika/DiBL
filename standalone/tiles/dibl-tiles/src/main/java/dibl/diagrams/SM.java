// @formatter:off
/*
 * Copyright 2013, J. Pol
 *
 * This file is part of free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation.
 *
 * This package is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details. A copy of the GNU General Public License is
 * available at <http://www.gnu.org/licenses/>.
 */
// @formatter:on
package dibl.diagrams;

import dibl.math.Matrix;
import dibl.math.StitchFlipper;

/** Matrix implementation for Stitches, the elements need no flipping. */
public class SM extends Matrix<StitchFlipper>
{
    private static final StitchFlipper FLIPPER = new StitchFlipper();

    /**
     * Creates a Stitch Matrix. Convenience constructor for
     * {@link Matrix#Matrix(String[][], dibl.math.Flipper).
     */
    public SM(String[][] input)
    {
        super(input, FLIPPER);
    }
}
