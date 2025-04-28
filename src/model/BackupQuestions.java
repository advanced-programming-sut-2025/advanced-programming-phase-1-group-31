package model;

public class BackupQuestions {
    private final StringBuilder backupQuestions = new StringBuilder();
    public final String Q1 = "What is your favorite toy's name?";
    public final String Q2 = "What is your grandfather's name?";
    public final String Q3 = "What is your favorite kids show??";

    public BackupQuestions() {
        backupQuestions.append("Choose a question to answer, this will later help in recovering your password:\n");
        backupQuestions.append("1- " + Q1 + "\n");
        backupQuestions.append("2- " + Q2 + "\n");
        backupQuestions.append("3- " + Q3 + "\n");
    }

    public void print() {
        System.out.println(backupQuestions);
    }
}
