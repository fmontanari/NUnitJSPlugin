package com.jetbrains.nunitjs.runner;

//import com.intellij.openapi.project.ProjectManager;
//import com.intellij.openapi.ui.Messages;
//import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class RunnerManager {

	public void deployRunner(String nunitPath){

		if (!hasToDeploy(nunitPath))
			return;

		CopyResource copyResource = new CopyResource();
		copyResource.writeEmbeddedResourceToLocalFile("nunit.js.properties", nunitPath);
	}

	private boolean hasToDeploy(String nunitPath) {

		FileInputStream deployedStream;
		try {
			deployedStream = new FileInputStream(nunitPath);
		} catch (FileNotFoundException e) {
			return true;
		}

		double deployedVersion = getRunnerVersion(deployedStream);

		InputStream embeddedStream = getClass().getResourceAsStream("nunit.js.properties");
		double embeddedVersion = getRunnerVersion(embeddedStream);

		
//		Messages.showMessageDialog(ProjectManager.getInstance().getOpenProjects()[0], String.valueOf(deployedVersion), "deployedVersion", null);
//		Messages.showMessageDialog(ProjectManager.getInstance().getOpenProjects()[0], String.valueOf(embeddedVersion), "embeddedVersion", null);

		return embeddedVersion > deployedVersion;
	}

	private double getRunnerVersion(InputStream inputStream){
		
		Scanner scanner = new Scanner(inputStream);
		try {

			String firstLine = scanner.nextLine();
			String versionStr = firstLine.replaceFirst("exports.version = ", "").replaceFirst(";", "").trim();
			double version = Double.parseDouble(versionStr);

			return version;

		} catch (Exception ex){
			//Messages.showMessageDialog(ProjectManager.getInstance().getOpenProjects()[0], ex.toString(), "Exception on read runner version", null);
			return -1;
		} finally {
			scanner.close();
		}
		
	}

}
