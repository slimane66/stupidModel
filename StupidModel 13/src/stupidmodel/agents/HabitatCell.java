/*
 * Version info:
 *     $HeadURL: https://cscs-repast-demos.googlecode.com/svn/richard/StupidModel/tags/2011_06_12_model_11/src/stupidmodel/agents/HabitatCell.java $
 *     $LastChangedDate: 2011-07-17 10:37:32 +0200 (V, 17 júl. 2011) $
 *     $LastChangedRevision: 740 $
 *     $LastChangedBy: richard.legendi@gmail.com $
 */
package stupidmodel.agents;

import java.util.ArrayList;
import java.util.Comparator;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameter;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.ContextUtils;
import repast.simphony.valueLayer.GridValueLayer;
import stupidmodel.StupidModelContextBuilder;
import stupidmodel.common.Constants;

/**
 * Habitat cell objects have instance variables for their food availability and
 * maximum food production rate.
 * 
 * @author Richard O. Legendi (richard.legendi)
 * @since 2.0-beta, 2011
 * @since Model 3
 * @version $Id: HabitatCell.java 183 2011-05-29 17:09:27Z
 *          richard.legendi@gmail.com $
 */
public class HabitatCell {

	/**
	 * Simple comparator for {@link HabitatCell} objects that orders them
	 * descending based on the current food availability.
	 * 
	 * @author Richard O. Legendi (richard.legendi)
	 * @since 2.0-beta, 2011
	 * @since Model 11
	 */
	public final static class HabitatCellFoodAvailabilityComparator implements
			Comparator<GridCell<HabitatCell>> {

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * Two cells are compared based on their
		 * {@link HabitatCell#foodAvailability} value by the default Java
		 * comparator for <code>double</code> values.
		 * </p>
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(final GridCell<HabitatCell> gc1,
				final GridCell<HabitatCell> gc2) {
			// Make sure if exactly 1 HabitatCell exists
			assert (hasOneCell(gc1));
			assert (hasOneCell(gc2));

			final HabitatCell cell = gc1.items().iterator().next();
			final HabitatCell bell = gc2.items().iterator().next();
			return -Double
					.compare(cell.foodAvailability, bell.foodAvailability);
		}

		/**
		 * A function to validate that the specified <code>GridCell</code>
		 * instance has exactly one {@link HabitatCell} instance associated.
		 * 
		 * <p>
		 * <i>Assertative function.</i>
		 * </p>
		 * 
		 * @param gc
		 *            the <code>GridCell</code> object to validate
		 * @return <code>true</code> if there is exactly one {@link HabitatCell}
		 *         on the specified cell; <code>false</code> otherwise
		 */
		private boolean hasOneCell(final GridCell<HabitatCell> gc) {
			final ArrayList<HabitatCell> list = new ArrayList<HabitatCell>();
			for (final HabitatCell cell : gc.items()) {
				list.add(cell);
			}

			return (1 == list.size());
		}
	}

	// Members declared package-protected to be able to use them in the tests

	/** Maximum food production rate is initialized to <code>0.01</code>. */
	protected double maximumFoodProductionRate = 0.01;

	/**
	 * Represents the actual food availability at this cell, initialized to
	 * <code>0.0</code>.
	 */
	protected double foodAvailability = 0.0;

	/** Location of this cell at the grid. */
	protected final int x, y;

	/**
	 * Creates a new instance of <code>HabitatCell</code>.
	 * 
	 * @param x
	 *            the specified <code>x</code> coordinate; <i>must be
	 *            non-negative</i>
	 * @param y
	 *            the specified <code>y</code> coordinate; <i>must be
	 *            non-negative</i>
	 */
	public HabitatCell(final int x, final int y) {
		if (x < 0) {
			throw new IllegalArgumentException(String.format(
					"Coordinate x = %d < 0.", x));
		}

		if (y < 0) {
			throw new IllegalArgumentException(String.format(
					"Coordinate y = %d < 0.", y));
		}

		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the maximum food production rate of the current cell.
	 * 
	 * <p>
	 * Parameter usage II: This method was created to demonstrate the usage of
	 * parameters declared at the level of agents.
	 * </p>
	 * 
	 * @return the value of {@link #maximumFoodProductionRate}
	 * @since Model 5
	 * @see StupidModelContextBuilder#build(repast.simphony.context.Context)
	 * @field maximumFoodProductionRate
	 */
	@Parameter(displayName = "Cell maximum food production rate", usageName = "maximumFoodProductionRate")
	public double getMaximumFoodProductionRate() {
		return maximumFoodProductionRate;
	}

	/**
	 * Sets the maximum food consumption rate of the current bug.
	 * 
	 * @param maximumFoodProductionRate
	 *            the new value of {@link #maximumFoodProductionRate}; <i>must
	 *            be non-negative</i>
	 * @since Model 5
	 */
	public void setMaximumFoodProductionRate(
			final double maximumFoodProductionRate) {
		if (maximumFoodProductionRate < 0) {
			throw new IllegalArgumentException(String.format(
					"Parameter maximumFoodProductionRate = %f < 0.",
					maximumFoodProductionRate));
		}

		this.maximumFoodProductionRate = maximumFoodProductionRate;
	}

	/**
	 * Returns the current food availability of this cell.
	 * 
	 * @return the <code>foodAvailability</code> of the cell; <i>a non-negative
	 *         value</i>
	 */
	public double getFoodAvailability() {
		return foodAvailability;
	}

	/**
	 * Sets the current food availability of this cell.
	 * 
	 * @param foodAvailability
	 *            the new <code>foodAvailability</code> value; <i>must be
	 *            non-negative</i>
	 */
	public void setFoodAvailability(final double foodAvailability) {
		if (foodAvailability < 0) {
			throw new IllegalArgumentException(String.format(
					"Parameter foodAvailability = %f < 0.", foodAvailability));
		}

		this.foodAvailability = foodAvailability;
	}

	/**
	 * Each time step, food availability is increased by food production. Food
	 * production is a random floating point number between zero and the maximum
	 * food production.
	 * 
	 * <p>
	 * Food production is scheduled before agent actions.
	 * </p>
	 */
	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public void growFood() {
		foodAvailability += RandomHelper.nextDoubleFromTo(0.0,
				maximumFoodProductionRate);

		final GridValueLayer foodValueLayer = (GridValueLayer) ContextUtils
				.getContext(this).getValueLayer(Constants.FOOD_VALUE_LAYER_ID);

		if (null == foodValueLayer) {
			throw new IllegalStateException(
					"Cannot locate food value layer with ID="
							+ Constants.FOOD_VALUE_LAYER_ID + ".");
		}

		foodValueLayer.set(getFoodAvailability(), x, y);
	}

	/**
	 * A {@link Bug} agent can consume food of the cell on which it is located
	 * at.
	 * 
	 * @param eatenFood
	 *            food to consume by the caller agent from this cell; <i>must be
	 *            non-negative and below the current
	 *            <code>foodAvailability</code></i>
	 */
	public void foodConsumed(final double eatenFood) {
		if (eatenFood < 0.0) {
			throw new IllegalArgumentException(String.format(
					"eatenFood = %f < 0.0", eatenFood));
		}

		if (eatenFood > foodAvailability) {
			throw new IllegalArgumentException(String.format(
					"eatenFood = %f > foodAvailability = %f", eatenFood,
					foodAvailability));
		}

		foodAvailability -= eatenFood;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Override default Java implementation just to have a nicer
		// representation
		return String.format(
				"HabitatCell @ location (%d, %d), foodAvailability=%f", x, y,
				foodAvailability);
	}

}
