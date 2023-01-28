package model;

public class IssueFields {

    private Fields fields;
    private String summary;

    public static class Fields {
        private Project project;
        private IssueType issuetype;
    }

    public static class Project {
        private String name;
    }

    public static class IssueType {

    }
}
