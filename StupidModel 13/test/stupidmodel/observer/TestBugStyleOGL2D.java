/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_12_model_11/test/stupidmodel/observer/TestBugStyleOGL2D.java $
 *     $LastChangedDate: 2011-06-08 08:16:34 +0200 (Sze, 08 jún. 2011) $
 *     $LastChangedRevision: 293 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel.observer;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import stupidmodel.agents.Bug;

/**
 * Simple tests for the created custom style.
 * 
 * <p>
 * Bug colors shade from white when size is zero to red.
 * </p>
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @version $Id: TestBugStyleOGL2D.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 */
public class TestBugStyleOGL2D {

	/** Style object to test. */
	private final BugStyleOGL2D style = new BugStyleOGL2D();

	/**
	 * Test if returned color is white when size is <code>0</code>.
	 */
	@Test
	public void testWhiteColor() {
		final Bug bug = new Bug();
		bug.setSize(0);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.WHITE, color);
	}

	/**
	 * Test if returned color is red when size is <code>255</code>.
	 */
	@Test
	public void testRedColor() {
		final Bug bug = new Bug();
		bug.setSize(255);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.RED, color);
	}

	/**
	 * Test if returned color is pink when size is (intermediate state).
	 */
	@Test
	public void testPinkColor() {
		final Bug bug = new Bug();
		bug.setSize(80);

		final Color color = style.getColor(bug);
		Assert.assertEquals(Color.PINK, color);
	}

}
