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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class TemplateTest
{
    private static final String BRICK_FOLDER = "src/main/assembly/input/PairTraversal/brick/";
    private static final String DIAMOND_FODLER = "src/main/assembly/input/PairTraversal/diamond/";
    private static final String OUTPUT_FOLDER = "target/TemplateDocTest/";

    @BeforeClass
    public static void createFolder()
    {
        new File(OUTPUT_FOLDER).mkdirs();
    }

    @Test
    public void mix3x3stitches() throws Exception
    {
        final String[][] stitches = new String[][] { {"tc", "tcptc", "tc"}, {"tc", "tcptc", "tc"}, {"tcptc", "tc", "tcptc"}};
        final Template template = new Template(new FileInputStream(BRICK_FOLDER + "3x3.svg"));
        template.replaceStitches(stitches);
        template.write(new FileOutputStream(OUTPUT_FOLDER + "3x3stitches.svg"));
    }

    @Test
    public void changeBoth3x3() throws Exception
    {
        final String[][] stitches = new String[][] { {"tcptc", "tc", "tcptc"}, {"tcptc", "tc", "tcptc"}, {"tc", "tcptc", "tc"}};
        final String[][] tuples = new String[][] { {"(1,0,0,1,0,-1,0,-1)", "(-1,0,0,1,1,-1,0,0)", "(1,1,0,0,0,-1,0,-1)"},//
                {"(-1,1,0,,1,0,-1,0,0)", "(0,1,0,0,1,-1,0,-1)", "(0,1,0,1,-1,0,0,-1)"},//
                {"(-1,1,0,1,-1,0,0,0)", "(1,1,0,0,-1,0,0,-1)", "(0,0,0,1,1,-1,0,-1)"}};
        final FileInputStream inputStream = new FileInputStream(DIAMOND_FODLER + "3x3.svg");
        final FileOutputStream outputStream = new FileOutputStream(OUTPUT_FOLDER + "3x3both.svg");
        new Template(inputStream).replaceBoth(stitches, tuples).write(outputStream);
    }

    @Test
    public void mix4x4() throws Exception
    {
        final String[][] stitches = new String[][] { {"tcptc", "tc", "tcptc", "tc"}, {"tc", "tcptc", "tc", "tcptc"}, {"tcptc", "tc", "tcptc", "tc"},
                {"tc", "tcptc", "tc", "tcptc"}};
        final Template t = new Template(new FileInputStream(DIAMOND_FODLER + "4x4.svg"));
        t.replaceStitches(stitches).write(new FileOutputStream(OUTPUT_FOLDER + "4x4_stitches.svg"));
    }
}
