/*
 * ------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 *
 * Copyright, 2003 - 2008
 * University of Konstanz, Germany
 * Chair for Bioinformatics and Information Mining (Prof. M. Berthold)
 * and KNIME GmbH, Konstanz, Germany
 *
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner or
 * as specified in the license file distributed with this product.
 *
 * If you have any questions please contact the copyright holder:
 * website: www.knime.org
 * email: contact@knime.org
 * ---------------------------------------------------------------------
 *
 * History
 *   Mar 18, 2008 (sellien): created
 */
package org.knime.base.util.coordinate;

/**
 *
 * @author Stephan Sellien, University of Konstanz
 */
public class DescendingNumericTickPolicyStrategy extends
        AscendingNumericTickPolicyStrategy {

    /**
     * ID of a descending policy. Used to ensure single instances in
     * {@link Coordinate}.
     */
    //hiding is necessary
    @SuppressWarnings("hiding")
    public static final String ID = "Descending";

    /**
     * Creates a policy strategy for descending order. Default name is
     * "Descending".
     *
     */
    public DescendingNumericTickPolicyStrategy() {
        super(ID);
    }

    /**
     * Creates a policy strategy for descending order.
     *
     * @param name the name of this strategy. Must not be null or empty.
     */
    public DescendingNumericTickPolicyStrategy(final String name) {
        super(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double interpolatePosition(final double value, final double min,
            final double max, final double absLength) {
        // just reverse
        return absLength
                - super.interpolatePosition(value, min, max, absLength);
    }
}
