package com.jetbrains.nunitjs;

import com.intellij.lang.javascript.psi.JSFile;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.jetbrains.nodejs.run.NodeJSRunner;

public class RunTestAction extends AnAction {

	private final TestRecognizer testRecognizer = new TestRecognizer();

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
		VirtualFile file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
		JSFile jsFile = (JSFile)event.getData(LangDataKeys.PSI_FILE);
		Editor editor = event.getData(PlatformDataKeys.EDITOR);

		NUnitLauncher nunitLauncher = new NUnitLauncher(new NodeJSRunner(), project);

		nunitLauncher.runTest(file.getPath(), testRecognizer.getCurrentTestName(jsFile, editor));
    }

	@Override
	public void update(AnActionEvent event) {
		super.update(event);

		Project project = event.getData(PlatformDataKeys.PROJECT);
		VirtualFile file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
		PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
		Editor editor = event.getData(PlatformDataKeys.EDITOR);

		JSFile jsFile = psiFile instanceof JSFile ? (JSFile)psiFile : null;

//		boolean visible = project != null && file != null && file.getName().endsWith("_fixture.js") && jsFile != null;
//		event.getPresentation().setVisible(visible);
		
		boolean enabled = project != null && file != null && file.getName().endsWith("_fixture.js") && jsFile != null && testRecognizer.isSelectionMatchTest(jsFile, editor);
		event.getPresentation().setEnabled(enabled);
	}
}
