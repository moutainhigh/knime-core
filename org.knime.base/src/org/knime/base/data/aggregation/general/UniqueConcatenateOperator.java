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
 * -------------------------------------------------------------------
 */

package org.knime.base.data.aggregation.general;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataType;
import org.knime.core.data.DataValue;
import org.knime.core.data.def.StringCell;

import org.knime.base.data.aggregation.AggregationOperator;
import org.knime.base.data.aggregation.GlobalSettings;
import org.knime.base.data.aggregation.OperatorColumnSettings;
import org.knime.base.data.aggregation.OperatorData;

import java.util.Set;

/**
 * Returns the concatenation of all different values per group.
 *
 * @author Tobias Koetter, University of Konstanz
 */
public class UniqueConcatenateOperator extends SetCellOperator {

    private final DataType m_type = StringCell.TYPE;

    /**Constructor for class Concatenate.
     * @param globalSettings the global settings
     * @param opColSettings the operator column specific settings
     */
    public UniqueConcatenateOperator(final GlobalSettings globalSettings,
            final OperatorColumnSettings opColSettings) {
        super(new OperatorData("uniqueConcatenate_V2.4", "Unique concatenate",
                "Unique concatenate", true, false,
                DataValue.class, true), globalSettings, opColSettings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataType getDataType(final DataType origType) {
        return m_type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AggregationOperator createInstance(
            final GlobalSettings globalSettings,
            final OperatorColumnSettings opColSettings) {
        return new UniqueConcatenateOperator(globalSettings,
                opColSettings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataCell getResultInternal() {
        final Set<DataCell> groupMembers = getGroupMembers();
        if (groupMembers.isEmpty()) {
            return DataType.getMissingCell();
        }
        final StringBuilder buf = new StringBuilder();
        boolean first = true;
        for (final DataCell val : groupMembers) {
            if (first) {
                first = false;
            } else {
                buf.append(getValueDelimiter());
            }
            buf.append(val.toString());
        }
        return new StringCell(buf.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return "Concatenates each member only once per group.";
    }
}
