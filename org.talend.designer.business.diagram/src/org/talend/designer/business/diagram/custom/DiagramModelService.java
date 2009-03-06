// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.business.diagram.custom;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.NoteEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.editparts.NoteAttachmentEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.business.BusinessType;
import org.talend.designer.business.diagram.custom.actions.CreateDiagramAction;
import org.talend.designer.business.diagram.custom.edit.parts.BaseBusinessItemRelationShipEditPart;
import org.talend.designer.business.diagram.custom.edit.parts.BusinessItemShapeEditPart;
import org.talend.designer.business.diagram.custom.properties.RepositoryFactoryProxyLabelProvider;
import org.talend.designer.business.model.business.diagram.edit.parts.BusinessProcessEditPart;
import org.talend.designer.business.model.business.diagram.part.BusinessDiagramEditor;
import org.talend.designer.business.model.business.provider.BusinessItemProviderAdapterFactory;
import org.talend.repository.editor.RepositoryEditorInput;

/**
 * DOC qian class global comment. An implementation of the IRunProcessService. <br/>
 * 
 * $Id: talend-code-templates.xml 1 2006-09-29 17:06:40 +0000 (星期�? 29 九月 2006) nrousseau $
 * 
 */

public class DiagramModelService implements IDiagramModelService {

    public IAction getCreateDiagramAction(boolean isToolbar) {
        return new CreateDiagramAction(isToolbar);
    }

    public void refreshBusinessModel(IEditorReference editors) {
        IEditorPart editor = editors.getEditor(true);
        if (editor instanceof BusinessDiagramEditor) {
            BusinessDiagramEditor businessDiagramEditor = (BusinessDiagramEditor) editor;
            businessDiagramEditor.refresh();
        }
    }

    public boolean isBusinessDiagramEditor(IEditorPart part) {
        if (part instanceof BusinessDiagramEditor) {
            return true;
        }
        return false;

    }

    public RepositoryEditorInput getBusinessDiagramEditorInput(IEditorPart editor) {
        if (editor instanceof BusinessDiagramEditor) {
            BusinessDiagramEditor businessEditor = (BusinessDiagramEditor) editor;
            return businessEditor.getDiagramEditorInput();
        }
        return null;

    }

    public DiagramEditPart getBusinessEditorProcess() {
        BusinessDiagramEditor editor = (BusinessDiagramEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getActiveEditor();
        return editor.getDiagramEditPart();
    }

    public BusinessType getBusinessModelType(Object part) {
        if (part instanceof BusinessProcessEditPart) {
            return BusinessType.PROCESS;
        } else if (part instanceof BusinessItemShapeEditPart) {
            return BusinessType.SHAP;
        } else if (part instanceof BaseBusinessItemRelationShipEditPart || part instanceof NoteAttachmentEditPart) {
            return BusinessType.CONNECTION;
        } else if (part instanceof NoteEditPart) {
            return BusinessType.NOTE;
        }

        return null;
    }

    public AdapterFactory getBusinessItemProviderAdapterFactory() {
        return new BusinessItemProviderAdapterFactory();
    }

    public AdapterFactoryLabelProvider getRepositoryFactoryProxyLabelProvider(AdapterFactory factory) {
        return new RepositoryFactoryProxyLabelProvider(factory);
    }

    public EObject getEObject(ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            return ((Node) ((BusinessItemShapeEditPart) ((IStructuredSelection) selection).getFirstElement()).getModel())
                    .getElement();
        }
        return null;
    }

    public ISelection getBusinessEditorSelection(IEditorPart editor) {
        if (editor instanceof BusinessDiagramEditor) {
            return ((BusinessDiagramEditor) editor).getSelection();
        }
        return null;
    }
}
