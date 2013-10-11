package com.jetbrains.nunitjs;

import com.intellij.conversion.ProjectSettings;
import com.intellij.conversion.impl.ProjectSettingsImpl;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.impl.ProjectImpl;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ComboboxWithBrowseButton;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NUnitJSConfig implements Configurable {
    private JPanel mainPanel;
    private ComboboxWithBrowseButton cmbNodePath;
    private ComboboxWithBrowseButton cmbNunitPath;
    private ComboboxWithBrowseButton cmbTestPath;

    private String storedNodePath;
    private String storedNunitPath;
    private String storedTestPath;

    public NUnitJSConfig() {
		
	}

	@Nls
	public String getDisplayName() {
		return "NUnitJS settings";
	}

	public Icon getIcon() {
		return null;
	}

	public String getHelpTopic() {
		return null;
	}

	public JComponent createComponent() {
		cmbNodePath.getComboBox().setEditable(true);
		cmbNunitPath.getComboBox().setEditable(true);
		cmbTestPath.getComboBox().setEditable(true);
		
        Project project = getCurrentProject();
        if (project == null){
            mainPanel.setEnabled(false);
			return mainPanel;
		}

        cmbNodePath.addBrowseFolderListener(project, new FileChooserDescriptor(true, false, false, false, false, false));
        cmbNunitPath.addBrowseFolderListener(project, new FileChooserDescriptor(false, true, false, false, false, false));
        cmbTestPath.addBrowseFolderListener(project, new FileChooserDescriptor(false, true, false, false, false, false));

		return mainPanel;
	}

	public boolean isModified() {
        String currentNodePath = (String)cmbNodePath.getComboBox().getEditor().getItem();
        String currentNunitPath = (String)cmbNunitPath.getComboBox().getEditor().getItem();
        String currentTestPath = (String)cmbTestPath.getComboBox().getEditor().getItem();

		return currentNodePath != storedNodePath || currentNunitPath != storedNunitPath || currentTestPath != storedTestPath;
	}

	public void apply() throws ConfigurationException {
        Project project = getCurrentProject();
        if (project == null)
            return;

        storedNodePath = (String)cmbNodePath.getComboBox().getEditor().getItem();
        storedNunitPath = (String)cmbNunitPath.getComboBox().getEditor().getItem();
        storedTestPath = (String)cmbTestPath.getComboBox().getEditor().getItem();

        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);

        propertiesComponent.setValue("NUnitJS_NodePath", storedNodePath);
        propertiesComponent.setValue("NUnitJS_NUnitPath", storedNunitPath);
        propertiesComponent.setValue("NUnitJS_TestPath", storedTestPath);
	}

	public void reset() {
        Project project = getCurrentProject();
        if (project == null)
            return;

		PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(project);

        storedNodePath = propertiesComponent.getValue("NUnitJS_NodePath");
        storedNunitPath = propertiesComponent.getValue("NUnitJS_NUnitPath");
        storedTestPath = propertiesComponent.getValue("NUnitJS_TestPath");

        cmbNodePath.getComboBox().getEditor().setItem(storedNodePath);
        cmbNunitPath.getComboBox().getEditor().setItem(storedNunitPath);
        cmbTestPath.getComboBox().getEditor().setItem(storedTestPath);
	}

	public void disposeUIResources() {
		
	}

    private Project getCurrentProject(){
        Project[] projects = ProjectManager.getInstance().getOpenProjects();

        if (projects.length == 0)
            return null;

        return projects[0];
    }
}



//            cmbNodePath.getComboBox().setEditable(true);
//        //nodeJSLocation.getComboBox().getEditor().setItem();
//        //nodeJSLocationLabel.setLabelFor(this.nodeJSLocation.getComboBox());
//
//        Project project = ProjectManager.getInstance().getOpenProjects()[0];
//
//        cmbNodePath.addBrowseFolderListener(project, new FileChooserDescriptor(true, false, false, false, false, false)
//        {
////            public boolean isFileSelectable(VirtualFile file) {
////                //return (file.isDirectory()) && (file.getName().indexOf("node") != -1);
////                return true;
////            }
//        });
//
//        //String nodePath = (String)textNodePath.getComboBox().getEditor().getItem();
