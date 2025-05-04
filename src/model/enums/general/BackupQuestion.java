package model.enums.general;

public enum BackupQuestion {
    BirthPlace("Where were you born?"),
    FavoriteToy("What is your favorite toy's name?"),
    FatherName("What is your father's name?");

    private final String question;

    BackupQuestion(String question) {
        this.question = question;
    }

    public String getQuestion(){
        return this.question;
    }
}
