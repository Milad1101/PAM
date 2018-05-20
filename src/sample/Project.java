package sample;

public class Project {

    private String title;
    private boolean[] dept;
    private boolean[] types;
    private String prof;

    public Project(String title, boolean[] dept, boolean[] types, String prof) {
        this.title = title;
        this.dept = dept;
        this.types = types;
        this.prof = prof;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean[] getDept() {
        return dept;
    }

    public void setDept(boolean[] dept) {
        this.dept = dept;
    }

    public boolean[] getTypes() {
        return types;
    }

    public void setTypes(boolean[] types) {
        this.types = types;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }
}
