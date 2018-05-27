package sample;

import java.util.ArrayList;

public class KnnAlgorithm {
    ArrayList<ProjectPoint> projectPoints;
    ArrayList<Project> projects;

    public KnnAlgorithm(ArrayList<Project> projects) {
        this.projects = new ArrayList<>(projects) ;
        projectPoints = new ArrayList<>();
        for(Project p : projects){
            projectPoints.add(new ProjectPoint(p));
        }
    }


    public ArrayList<Project> getGoodProjects(int k ,ProjectPoint target){

        ArrayList<Project> res =new ArrayList<>();

       ArrayList<ProjectPoint> tempProjectPoints = new ArrayList<>(projectPoints);

        ArrayList<Project> tempProjects = new ArrayList<>(projects);

        int n = 0;

        while (n < k) {

            ArrayList<Double> distances = new ArrayList<>();
            for (ProjectPoint pp : tempProjectPoints) {
                distances.add(target.distenc(pp));
            }


            double min = 10000000.0;

            for (double d : distances) {

                if (d < min) {
                    min = d;
                }

            }


            ArrayList<ProjectPoint> newTempProjectPoints = new ArrayList<>();
            ArrayList<Project> newTempProjects = new ArrayList<>();

            for (int i = 0; i < tempProjectPoints.size(); i++) {

                if(distances.get(i) == min){
                   if(!projectIsTaken(tempProjects.get(i),res))
                        res.add(tempProjects.get(i));
                }else {
                    newTempProjectPoints.add(tempProjectPoints.get(i));
                    newTempProjects.add(tempProjects.get(i));
                }

            }


            tempProjectPoints = newTempProjectPoints;
            tempProjects = newTempProjects;


            n++;
        }
        return res;
    }


    public ArrayList<Project> getGoodProfessors(int k ,ProjectPoint target){

        ArrayList<Project> res =new ArrayList<>();
        ArrayList<ProjectPoint> tempProjectPoints = new ArrayList<>(projectPoints);
        ArrayList<Project> tempProjects = new ArrayList<>(projects);

        int n = 0;
        while (n < k) {

            ArrayList<Double> distances = new ArrayList<>();
            for (ProjectPoint pp : tempProjectPoints) {
                distances.add(target.distenc(pp));
            }


            double min = 10000.0;

            for (double d : distances) {
                if (d < min) {
                    min = d;
                }
            }

            ArrayList<ProjectPoint> newTempProjectPoints = new ArrayList<>();
            ArrayList<Project> newTempProjects = new ArrayList<>();

            for (int i = 0; i < tempProjectPoints.size(); i++) {

                if(distances.get(i) == min){
                    if(!professorIsTaken(tempProjects.get(i),res))
                        res.add(tempProjects.get(i));
                }else {
                    newTempProjectPoints.add(tempProjectPoints.get(i));
                    newTempProjects.add(tempProjects.get(i));
                }

            }


            tempProjectPoints = newTempProjectPoints;
            tempProjects = newTempProjects;


            n++;
        }
        return res;
    }



    private boolean projectIsTaken(Project project , ArrayList<Project> projects){

        for(Project p : projects){
            if(p.getTitle().equals(project.getTitle()))
                return true;
        }

        return false;
    }



    private boolean professorIsTaken(Project project , ArrayList<Project> projects){

        for(Project p : projects){
            if(p.getProf().getId() == project.getProf().getId())
                return true;
        }

        return false;
    }

}
