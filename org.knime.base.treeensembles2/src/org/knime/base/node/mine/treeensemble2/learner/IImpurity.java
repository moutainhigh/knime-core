/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ------------------------------------------------------------------------
 *
 * History
 *   May 19, 2012 (wiswedel): created
 */
package org.knime.base.node.mine.treeensemble2.learner;

/**
 *
 * @author Bernd Wiswedel, KNIME AG, Zurich, Switzerland
 */
public interface IImpurity {

    /**
     * Calculates the impurity of a partition
     *
     * @param targetCounts the counts for the individual target values
     * @param partitionWeight the total weight of the partition (must be the sum of <b>targetCounts</b>
     * @return the impurity of the partition
     */
    public double getPartitionImpurity(final double[] targetCounts, final double partitionWeight);

    /**
     * Calculates the impurity after splitting
     *
     * @param partitionValues the impurity of the individual partitions
     * @param partitionWeights the weights of the individual partitions
     * @param totalWeight the sum of the weight of all partitions
     * @return the impurity after splitting
     */
    public double getPostSplitImpurity(final double[] partitionValues, final double[] partitionWeights,
        final double totalWeight);

    /**
     * Calculates the gain of a split
     *
     * @param priorImpurity the impurity before splitting
     * @param postSplitImpurity the impurity after splitting
     * @param partitionWeights the weights of the partitions
     * @param totalWeight the total weight (sum of <b>partitionWeights</b>)
     * @return the gain of a split
     */
    public double getGain(final double priorImpurity, final double postSplitImpurity, final double[] partitionWeights,
        final double totalWeight);
}
