package test;

import model.RequestCapability;
import utils.ProjectInfo;

public class JiraissueType implements RequestCapability {

    public static void main(String[] args) {
        String baseUri = "https://restassuredapi.atlassian.net";
        String projectKey = "RA";

        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        System.out.println("Task ID: " + projectInfo.getIssueTypeId("task"));
    }
}
