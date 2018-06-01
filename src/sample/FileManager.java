package sample;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    String projectsFileName = "projects.pcas";
    String questionsFileName = "questions.pcas";
    String professorsFileName = "professors.pcas";

    public void writeProjects(ArrayList<Project> projects) throws IOException {

        File f = new File(projectsFileName);

            FileOutputStream fos = new FileOutputStream(f);

            ObjectOutputStream oo = new ObjectOutputStream(fos);

            for(Project p : projects){
                oo.writeObject(p);
            }

    }


    public ArrayList<Project> readProjects() throws IOException, ClassNotFoundException {

        ArrayList<Project> res = new ArrayList<>();

        File f = new File(projectsFileName);


            FileInputStream fis = new FileInputStream(f);

            ObjectInputStream ois = new ObjectInputStream(fis);

            Project p;
            while ((p = (Project) ois.readObject()) != null){
                res.add(p);
            }


        return res;
    }


    public void writeQuestions(ArrayList<Questions> questions) throws IOException {
        File f = new File(questionsFileName);

        FileOutputStream fos = new FileOutputStream(f);

        ObjectOutputStream oo = new ObjectOutputStream(fos);

        for(Questions q : questions){
            oo.writeObject(q);
        }

    }
    public ArrayList<Questions> readQuestions() throws IOException, ClassNotFoundException {

        ArrayList<Questions> res = new ArrayList<>();

        File f = new File(questionsFileName);


        FileInputStream fis = new FileInputStream(f);

        ObjectInputStream ois = new ObjectInputStream(fis);

        Questions q;
        while ((q = (Questions) ois.readObject()) != null){
            res.add(q);
        }


        return res;
    }



    public void writeProfessors(ArrayList<Professor> professors) throws IOException {
        File f = new File(professorsFileName);

        FileOutputStream fos = new FileOutputStream(f);

        ObjectOutputStream oo = new ObjectOutputStream(fos);

        for(Professor p : professors){
            oo.writeObject(p);
        }

    }
    public ArrayList<Professor> readProfessors() throws IOException, ClassNotFoundException {

        ArrayList<Professor> res = new ArrayList<>();

        File f = new File(professorsFileName);


        FileInputStream fis = new FileInputStream(f);

        ObjectInputStream ois = new ObjectInputStream(fis);

        Professor p;
        while ((p = (Professor) ois.readObject()) != null){
            res.add(p);
        }


        return res;
    }


}
