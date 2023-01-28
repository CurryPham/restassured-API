package builder;

import model.IssueFields;

public class IsssueContentBuilder{


    public static String build(String projectKey, String taskTypeId, String summary){
        IssueFields.IssueType issueType = new IssueFields.IssueType(taskTypeId);
        IssueFields.Project project = new IssueFields.Project(projectKey);
        IssueFields.Fields fields = new IssueFields.Fields(project, issueType, summary);
        IssueFields issueFields = new IssueFields(fields);

        return BodyJSONBuilder.getJSONContent(issueFields);
    }
}
