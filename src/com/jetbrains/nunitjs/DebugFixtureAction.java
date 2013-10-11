package com.jetbrains.nunitjs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.nodejs.debug.NodeJSDebugRunner;

import java.util.ArrayList;

public class DebugFixtureAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
		VirtualFile[] files = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

		NUnitLauncher nunitLauncher = new NUnitLauncher(new NodeJSDebugRunner(), project);

		String[] paths = new String[files.length];
		for (int i = 0; i < files.length; i++){
			paths[i] = files[i].getPath();
		}

		nunitLauncher.runFixture(paths);
    }

	@Override
	public void update(AnActionEvent event) {
		super.update(event);
		Project project = event.getData(PlatformDataKeys.PROJECT);
		VirtualFile[] files = event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);

		boolean enabled =
				project != null &&
				files != null &&
				files.length > 0;
		// Visibility
		//event.getPresentation().setVisible(visible);
		// Enable or disable
		event.getPresentation().setEnabled(enabled);
	}
}
