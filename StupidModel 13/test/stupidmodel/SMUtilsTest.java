/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_12_model_11/test/stupidmodel/SMUtilsTest.java $
 *     $LastChangedDate: 2011-06-08 08:16:34 +0200 (Sze, 08 jún. 2011) $
 *     $LastChangedRevision: 293 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.TestGridCellFactory;
import repast.simphony.space.grid.GridPoint;
import stupidmodel.common.SMUtils;

/**
 * Test methods for the {@link SMUtils} class.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: SMUtilsTest.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 * @see SMUtils
 */
public class SMUtilsTest {

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for <code>null</code>
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetFreeGridCellsForNull() {
		SMUtils.getFreeGridCells(null);
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for empty list.
	 */
	@Test
	public void testGetFreeGridCellsForEmptyList() {
		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(new ArrayList<GridCell<Object>>());
		Assert.assertEquals(ret, Collections.emptyList());
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for one empty element.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleEmptyList() {
		final ArrayList<GridCell<Object>> neighborhood = new ArrayList<GridCell<Object>>();

		final GridCell<Object> gridCell = new GridCell<Object>(new GridPoint(1,
				1), Object.class);
		neighborhood.add(gridCell);

		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(neighborhood);
		// NB: Function equals() is not specified for GridCell
		Assert.assertEquals(ret, Collections.singletonList(gridCell));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for empty elements.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetFreeGridCellsForMultipleEmptyElements() {
		final ArrayList<GridCell<Object>> neighborhood = new ArrayList<GridCell<Object>>();

		final GridCell<Object> gridCell1 = new GridCell<Object>(new GridPoint(
				1, 1), Object.class);
		final GridCell<Object> gridCell2 = new GridCell<Object>(new GridPoint(
				2, 2), Object.class);

		neighborhood.add(gridCell1);
		neighborhood.add(gridCell2);

		final List<GridCell<Object>> ret = SMUtils
				.getFreeGridCells(neighborhood);
		// NB: Function equals() is not specified for GridCell
		Assert.assertEquals(ret, Arrays.asList(gridCell1, gridCell2));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for one empty and one
	 * occupied element.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleList() {
		final ArrayList<GridCell<String>> neighborhood = new ArrayList<GridCell<String>>();

		final GridCell<String> oneObjectCell = TestGridCellFactory
				.createGridCellToTest(new GridPoint(1, 1), String.class, "A");
		final GridCell<String> emptyCell = new GridCell<String>(new GridPoint(
				2, 2), String.class);

		neighborhood.add(oneObjectCell);
		neighborhood.add(emptyCell);

		final List<GridCell<String>> ret = SMUtils
				.getFreeGridCells(neighborhood);

		Assert.assertEquals(ret, Collections.singletonList(emptyCell));
	}

	/**
	 * Testing {@link SMUtils#getFreeGridCells(List)} for occupied elements.
	 */
	@Test
	public void testGetFreeGridCellsForSimpleOccupiedList() {
		final ArrayList<GridCell<String>> neighborhood = new ArrayList<GridCell<String>>();

		final GridCell<String> cell = TestGridCellFactory.createGridCellToTest(
				new GridPoint(1, 1), String.class, "A");

		final GridCell<String> bell = TestGridCellFactory.createGridCellToTest(
				new GridPoint(2, 2), String.class, "B");

		neighborhood.add(cell);
		neighborhood.add(bell);

		final List<GridCell<String>> ret = SMUtils
				.getFreeGridCells(neighborhood);

		Assert.assertEquals(ret, Collections.emptyList());
	}

	// ========================================================================
	// === Testing function randomElementOf() =================================

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for <code>null</code>
	 * parameter.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRandomElementOfForNull() {
		SMUtils.randomElementOf(null);
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for empty list.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRandomElementOfForEmptyList() {
		SMUtils.randomElementOf(Collections.emptyList());
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for a singleton list.
	 */
	@Test
	public void testRandomElementOfForSingletonList() {
		final String value = "A";
		final String randomElement = SMUtils.randomElementOf(Collections
				.singletonList(value));

		Assert.assertEquals(value, randomElement);
	}

	/**
	 * Testing {@link SMUtils#randomElementOf(List)} for a sample list.
	 */
	@Test
	public void testRandomElementOfContainsResult() {
		final List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		final String randomElement = SMUtils.randomElementOf(list);

		Assert.assertTrue(list.contains(randomElement));
	}

	/**
	 * Assert that {@link SMUtils#randomElementOf(List)} does not modify the
	 * original list.
	 */
	@Test
	public void testRandomElementOfDoesNotModifiesOriginalList() {
		final List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		final List<String> backupList = new ArrayList<String>(list);

		SMUtils.randomElementOf(list);
		Assert.assertEquals(backupList, list);
	}
}
