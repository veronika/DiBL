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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.JDOMException;

import dibl.math.MatrixTransformer;
import dibl.math.TupleTransformer;

public class Generator
{
    private static String CFG = "cfg/"; // not final to allow WhiteboxTest
    private static final Map<String, TemplateDoc> templates = new HashMap<String, TemplateDoc>();
    private static int maxPermutations = 500;

    /**
     * Generates thread diagrams for permutations of the specified stitches.
     * 
     * @param template
     *        a diagram generated by {@link #symetricVariants(PairTraversalPattern, File, String...)}
     * @param folder
     *        an existing folder where the numbered SVG files are written
     * @param stitches
     * @throws FileNotFoundException
     *         if the folder does not exist
     * @throws IOException
     *         in case of trouble writing a diagram
     * @throws JDOMException
     *         in case of trouble with the content of the template
     */
    public static void permutations(TemplateDoc template, final File folder, final String... stitches) throws FileNotFoundException, IOException, JDOMException
    {
        final int nrOfCells = template.getNrOfCols() * template.getNrOfRows();
        final String regexp = possiblePermutations(template, stitches).toString();

        int j = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++)
        {
            final String permutation = pad(nrOfCells, Integer.toString(i, stitches.length));
            if (permutation.length() > nrOfCells)
                break;
            if (!permutation.matches(regexp))
                continue; // skip variations on empty nodes
            if (j++ > getMaxPermutations())
                break;

            String[][] stitchMatrix = toMatrix(template, permutation.toCharArray(), stitches);
            template.replaceStitches(stitchMatrix);
            writeVariation(folder + "/" + permutation + ".svg", template);
        }
    }

    private static String[][] toMatrix(TemplateDoc template, char[] chars, final String... stitches)
    {
        int nrOfRows = template.getNrOfRows();
        int nrOfCols = template.getNrOfCols();
        String[][] stitchMatrix = new String[nrOfRows][nrOfCols];
        for (int row = 0; row < nrOfRows; row++)
            for (int col = 0; col < nrOfCols; col++)
            {
                int j = row * nrOfCols + col;
                stitchMatrix[row][col] = stitches[(chars[j] - '0') % stitches.length];
            }
        return stitchMatrix;
    }

    private static StringBuffer possiblePermutations(TemplateDoc template, final String... stitches)
    {
        final StringBuffer regexp1 = new StringBuffer();

        Map<String, Boolean> emptyCells = template.getEmptyCells();
        for (String cellID : emptyCells.keySet())
        {
            if (emptyCells.get(cellID))
                regexp1.append("0");
            else
                regexp1.append("[" + "0123456789".substring(0, stitches.length) + "]");
        }
        return regexp1;
    }

    /**
     * Generates thread diagrams from the specified pair traversal pattern and from its mirrored and
     * rotated versions.
     * 
     * @param primaryPattern
     * @param folder
     *        an existing folder where the numbered SVG files are written
     * @param stitches
     *        stitches are repeated for all positions in the base tile
     * @throws IOException
     * @throws FileNotFoundException
     *         if the output folder does not exist or the configuration folder has no template with the
     *         same dimensions as the specified pattern
     * @throws JDOMException
     *         in case of trouble with the content of the template
     */
    public static void symetricVariants(String[][] tuples, final File folder, final String[] stitches) throws IOException, FileNotFoundException, JDOMException
    {
        final int rows = tuples.length;
        final int cols = tuples[0].length;
        String[][] stitchMatrix = buildStitchesMatrix(stitches, rows, cols);

        final MatrixTransformer<TupleTransformer> t = new MatrixTransformer<TupleTransformer>(new TupleTransformer());
        String[][][] variants = {tuples, t.flipNW2SE(t.flipNE2SW(tuples)), t.flipNE2SW(tuples), t.flipNW2SE(tuples)};

        final TemplateDoc template = getTemplate(rows + "x" + cols);
        int i=1;
        for (String[][] variant : variants)
        {
            template.replaceBoth(stitchMatrix,variant);
            template.write(new FileOutputStream(folder + "/" + (i++) + ".svg"));
        }
    }

    private static String[][] buildStitchesMatrix(final String[] args, final int numberOfRows, final int numberOfColumns)
    {
        final String[][] stitches = new String[numberOfRows][numberOfColumns];
        for (int r = 0; r < stitches.length; r++)
            for (int c = 0; c < stitches[r].length; c++)
                stitches[r][c] = args[(r * stitches.length + c) % (args.length)];
        return stitches;
    }

    /**
     * clears the cash of previously loaded templates.
     */
    public static void resetTemplates()
    {
        templates.clear();
    }

    private static TemplateDoc getTemplate(final String dimensions) throws IOException, JDOMException, FileNotFoundException
    {
        if (!templates.containsKey(dimensions))
            templates.put(dimensions, new TemplateDoc(new FileInputStream(CFG + dimensions + ".svg")));
        return templates.get(dimensions);
    }

    private static String pad(final int nrOfCells, final String variation)
    {
        if (variation.length() < nrOfCells)
            return "00000000000000000".substring(0, nrOfCells - variation.length()) + variation;
        return variation;
    }

    private static void writeVariation(final String fileName, final TemplateDoc template) throws FileNotFoundException, IOException, JDOMException
    {
        final FileOutputStream out = new FileOutputStream(fileName);
        try
        {
            template.write(out);
        }
        finally
        {
            out.close();
        }
    }

    public static int getMaxPermutations()
    {
        return maxPermutations;
    }

    public static void setMaxPermutations(int maxPermutations)
    {
        Generator.maxPermutations = maxPermutations;
    }
}