package sample;

public class ProjectPoint {

    double type[];
    Project project;

    public ProjectPoint(Project project){

        this.project = project;
        type = new  double[project.getTypes().length];
        calculatePos();

    }


    private void calculatePos(){

        int numOfTypes =0;

        for(boolean b: project.getTypes()){
            if(b)numOfTypes++;
        }

        if(numOfTypes == 0) {
            type[0] = 1.0;
            return;
        }
        double v;
        v = 1.0 / numOfTypes;

        for(int i = 0 ; i < type.length ; i++){
            if(project.getTypes()[i]){
                type[i] = v;
            }else{
                type[i] = 0.0;
            }
        }

    }


    public double distenc(ProjectPoint p){
        double res = 0.0;

        double sumOfDifSum = 0.0;

        for(int i = 0; i < type.length ; i++){
            sumOfDifSum += Math.pow((type[i] - p.getType()[i]),2);
        }

        res = Math.sqrt(sumOfDifSum);

        return res;
    }



    public double[] getType() {
        return type;
    }

    public void setType(double[] type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
