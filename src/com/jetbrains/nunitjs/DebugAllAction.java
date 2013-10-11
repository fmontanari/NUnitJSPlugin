package com.jetbrains.nunitjs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.jetbrains.nodejs.debug.NodeJSDebugRunner;

public class DebugAllAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		Project project = event.getData(PlatformDataKeys.PROJECT);

		NUnitLauncher nunitLauncher = new NUnitLauncher(new NodeJSDebugRunner(), project);

		nunitLauncher.runAll();
	}

	@Override
	public void update(AnActionEvent event) {
		super.update(event);
		Project project = event.getData(PlatformDataKeys.PROJECT);

		boolean enabled = project != null;
		// Visibility
		//event.getPresentation().setVisible(visible);
		// Enable or disable
		event.getPresentation().setEnabled(enabled);
	}
}
