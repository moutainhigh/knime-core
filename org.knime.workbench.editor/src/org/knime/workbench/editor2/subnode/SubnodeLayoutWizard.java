/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
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
 * ---------------------------------------------------------------------
 *
 * History
 *   24.03.2014 (Christian Albrecht, KNIME.com AG, Zurich, Switzerland): created
 */
package org.knime.workbench.editor2.subnode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.knime.core.node.wizard.WizardNode;
import org.knime.core.node.wizard.WizardNodeLayoutInfo;
import org.knime.core.node.workflow.NodeID;
import org.knime.core.node.workflow.SubNodeContainer;
import org.knime.core.node.workflow.WorkflowManager;
import org.knime.workbench.editor2.ImageRepository;

/**
 *
 * @author Christian Albrecht, KNIME.com AG, Zurich, Switzerland
 */
public class SubnodeLayoutWizard extends Wizard {

    private final SubNodeContainer m_subNodeContainer;
    private final EditPartViewer m_viewer;
    private SubnodeLayoutPage m_page;

    /**
     * @param viewer the viewer
     * @param container the subnode container
     *
     */
    public SubnodeLayoutWizard(final EditPartViewer viewer, final SubNodeContainer container) {
        super();
        m_viewer = viewer;
        m_subNodeContainer = container;
        setHelpAvailable(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPages() {
        setWindowTitle("Layout Subnode Wizard");
        setDefaultPageImageDescriptor(ImageDescriptor.createFromImage(ImageRepository.getImage("icons/layout_55.png")));
        WorkflowManager wfManager = m_subNodeContainer.getWorkflowManager();
        Map<NodeID, SubNodeContainer> nestedSubnodes = wfManager.findNodes(SubNodeContainer.class, false);
        Map<NodeID, WizardNode> viewNodes = wfManager.findNodes(WizardNode.class, false);
        List<NodeID> nodeIDs = new ArrayList<NodeID>();
        nodeIDs.addAll(viewNodes.keySet());
        for (NodeID subnodeID : nestedSubnodes.keySet()) {
            WorkflowManager nestedWFManager = nestedSubnodes.get(subnodeID).getWorkflowManager();
            if (!nestedWFManager.findNodes(WizardNode.class, true).isEmpty()) {
                nodeIDs.add(subnodeID);
            }
        }
        Collections.sort(nodeIDs);
        m_page = new SubnodeLayoutPage("Change the layout configuration");
        m_page.setNodes(wfManager, m_subNodeContainer, nodeIDs);
        addPage(m_page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canFinish() {
        if (m_page == null || !m_page.isLayoutMapValid()) {
            return false;
        }
        return super.canFinish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performFinish() {
        if (m_page.isLayoutMapValid()) {
            Map<Integer, WizardNodeLayoutInfo>layoutInfo = m_page.getLayoutMap();
            m_subNodeContainer.setLayoutInfo(layoutInfo);
            return true;
        } else {
            return false;
        }
    }
}