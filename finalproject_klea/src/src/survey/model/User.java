package src.survey.model;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/

public abstract class User { //main abstract class for users
    private String email;
    private String name;


    private String password;
    private boolean member;

    public User(String email, String name, String password, boolean member) {
        this.email = email; //all the needed features
        this.name = name;
        this.password = password;
        this.member = member;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }


    public boolean checkPassword(String attempt) {
        return attempt.equals(password);
    }

    public boolean updatePassword(String attempt, String newPword) { //updates the password
        boolean updated = false;
        if (checkPassword(attempt)) {
            this.password = newPword;
            updated = true;

        }
        return updated;
    }

    @Override
    public int hashCode() {
        //hashCode and equals go together, so if my equals method ends up saying case doesn't matter, then I can't
        //make the case matter for hashCode, so making email and name lowercase when doing hashCode
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.toLowerCase().hashCode());
        result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        return email.equalsIgnoreCase(other.email) && name.equalsIgnoreCase(other.name)
                && password.equals(other.password);
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", name=" + name + ", password=" + password + "]";
    }


    public String toFileString() {
        return this.isMember() + "," + this.email + ","
                + this.name + "," + this.password;
    }
}
