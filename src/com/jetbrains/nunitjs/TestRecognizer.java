package com.jetbrains.nunitjs;

import com.intellij.lang.javascript.psi.JSFile;
import com.intellij.lang.javascript.psi.JSSourceElement;
import com.intellij.openapi.editor.Editor;

public class TestRecognizer {
	public TestRecognizer() {
	}

	public boolean isSelectionMatchTest(JSFile jsFile, Editor editor) {
		String testName = getCurrentTestName(jsFile, editor);

		return testName != "";
	}

	public String getCurrentTestName(JSFile jsFile, Editor editor) {

		int selectionStart;
		try {
			selectionStart = editor.getSelectionModel().getSelectionStart();
		} catch (Exception ex) {
			return "";
		}

		for (JSSourceElement element : jsFile.getStatements()) {
			if (element.getTextRange().contains(selectionStart)) {
				return getTestName(element);
			}
		}

		return "";
	}

	private String getTestName(JSSourceElement element) {

		String funcName = "";

		try {
			funcName = element.getFirstChild().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getNextSibling().getText();
		} catch (Exception ex) {
			return "";
		}

		if (!funcName.startsWith("test"))
			return "";

		return funcName;
	}
}