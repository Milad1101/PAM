package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    ListView<String> projectList1 , professorList1;
    @FXML
    Button answerBut , selectProjectBut1 ,selectProfessorBut1 ;
    @FXML
    TextArea questionArea;
    @FXML
    ChoiceBox answerMenu;


    private int currentQuestionIndex = 0;
    private boolean[] questionsAnswers;
    private boolean[][] shouldAskQuestions;
    private DBManager dbManager;
    private FileManager fileManager;
    private ArrayList<Questions> questions;
    private ArrayList<Project> projects;
    private ArrayList<Professor> professors;
    private Project targetProject;
    private ArrayList<Project> goodProjects;
    private Project selectedProject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileManager = new FileManager();
        questions = new ArrayList<>();
        shouldAskQuestions = new boolean[14][14];
        questionsAnswers = new boolean[14];
        fillShouldAskQuestions(shouldAskQuestions);

        try {

            questions = fileManager.readQuestions();
            projects = fileManager.readProjects();
            professors = fileManager.readProfessors();

        }catch (Exception e){

            dbManager = new DBManager();
            questions = dbManager.getQuestions();
            projects = dbManager.getProjects();
            professors = dbManager.getProfessors();

            try {

                fileManager.writeProjects(projects);
                fileManager.writeQuestions(questions);
                fileManager.writeProfessors(professors);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }


        setQuestion(questions.get(0));
        boolean[] dept = new boolean[3];
        boolean[] types = new boolean[14];
        targetProject = new Project("",dept,types,new Professor());




    }











    private void fillShouldAskQuestions(boolean[][] saq){

        for(int i = 0 ; i < DBManager.projectTypes.length ; i++) {
            for (Project p : projects)
                for(int j =0 ; j < DBManager.projectTypes.length ; j++)
                    if(p.getTypes()[i]&&p.getTypes()[j]) saq[i][j] = true;
        }

    }












    public void answerButClicked(ActionEvent event) {
        String answer;

        try {
            answer = answerMenu.getValue().toString();
        }catch (Exception e){
            return;
        }

        if(currentQuestionIndex < 3) {

            if (answer.equals(questions.get(currentQuestionIndex).getAnswers().get(0))) {
                targetProject.getDept()[currentQuestionIndex] = true;
                targetProject.getDept()[(currentQuestionIndex + 1) % 3] = false;
                targetProject.getDept()[(currentQuestionIndex + 2) % 3] = false;
                currentQuestionIndex = 2;
            } else if(currentQuestionIndex == 1){
                targetProject.getDept()[currentQuestionIndex] = false;
                currentQuestionIndex = 2;
                targetProject.getDept()[currentQuestionIndex] = true;
                targetProject.getDept()[(currentQuestionIndex + 1) % 3] = false;
                targetProject.getDept()[(currentQuestionIndex + 2) % 3] = false;
            }else {
                targetProject.getDept()[currentQuestionIndex] = false;
            }
            
        } else {
            
            if (answer.equals(questions.get(currentQuestionIndex).getAnswers().get(0))) {
                targetProject.getTypes()[questions.get(currentQuestionIndex).getType()] = true;
                questionsAnswers[questions.get(currentQuestionIndex).getType()] =true;
            } else {
                targetProject.getTypes()[questions.get(currentQuestionIndex).getType()] = false;
                questionsAnswers[questions.get(currentQuestionIndex).getType()] =false;
            }
            
        }

        nextQuestion();
    }










    private void nextQuestion(){

        currentQuestionIndex++;
        boolean enough= false;
        while (!enough) {

            enough =true;
            for (int i = 0; i < DBManager.projectTypes.length; i++)
                if (questionsAnswers[i])
                    if (!shouldAskQuestions[i][questions.get(currentQuestionIndex).getType()]) {
                        currentQuestionIndex++;
                        enough = false;
                        if(currentQuestionIndex >= questions.size()) break;
                    }

            if(currentQuestionIndex >= questions.size())break;

        }

        if(currentQuestionIndex >= questions.size()){
            stage2();
            return;
        }

        setQuestion(questions.get(currentQuestionIndex));
    }










    private void stage2() {

        questionArea.setText("شكرا على الإجابة...\n يمكنك الإختيار من المشاريع المقترحة في الاسفل.");
        answerMenu.setDisable(true);
        answerBut.setDisable(true);
        projectList1.setDisable(false);
        projectList1.setDisable(false);
        selectProjectBut1.setDisable(false);
        knnForProjectMethod(targetProject);

    }









    private void setQuestion(Questions question){
        questionArea.setText(question.getQuestion());
        answerMenu.setItems(FXCollections.observableArrayList(questions.get(currentQuestionIndex).getAnswers()));
    }












    private void knnForProjectMethod(Project project){

        ArrayList<Project> dataProjects = new ArrayList<>();
        int targetDept;
        if(targetProject.getDept()[0])
            targetDept = 0;
        else if(targetProject.getDept()[1])
            targetDept = 1;
        else
            targetDept = 2;

        for(Project p : projects)
            if(p.getDept()[targetDept]) dataProjects.add(p);

        KnnAlgorithm knnAlgorithm = new KnnAlgorithm(dataProjects);
        goodProjects = knnAlgorithm.getGoodProjects(3,new ProjectPoint(project));

        for(Project p : goodProjects)
            if(p.getDept()[targetDept])
                projectList1.getItems().add(p.getTitle());

    }












    private void knnForProfessorsMethod(Project project){

        ArrayList<Project> dataProjects = new ArrayList<>();
        int targetDept;
        if(targetProject.getDept()[0])
            targetDept = 0;
        else if(targetProject.getDept()[1])
            targetDept = 1;
        else
            targetDept = 2;

        for(Project p : projects)
            if(p.getDept()[targetDept]) dataProjects.add(p);

        KnnAlgorithm knnAlgorithm = new KnnAlgorithm(dataProjects);
        ArrayList<Project> goodProjectsForProfessors = knnAlgorithm.getGoodProfessors(3, new ProjectPoint(project));

        for(Project p : goodProjectsForProfessors)
                if(p.getDept()[targetDept] && p.getProf().getDept() == targetDept)
                    professorList1.getItems().add(p.getProf().getName());

    }










    public void selectProjectBut1Clicked(ActionEvent event) {

        professorList1.setDisable(false);
        selectProfessorBut1.setDisable(false);
        String selectedProjectTitle = projectList1.getSelectionModel().getSelectedItem();
        targetProject.setTitle(selectedProjectTitle);

        for(Project p : goodProjects)
            if(p.getTitle().equals(selectedProjectTitle)) selectedProject = p;

        knnForProfessorsMethod(selectedProject);
        projectList1.setDisable(true);
        selectProjectBut1.setDisable(true);

    }









    public void selectProfessorBut1Clicked(ActionEvent event) {

        String selectedProf = professorList1.getSelectionModel().getSelectedItem();
        for(Professor p:professors)
            if(p.getName().equals(selectedProf)) {
                targetProject.setProf(p);
                break;
            }

        String typeStr = "";
        for(int i = 0 ; i < DBManager.projectTypes.length;i++)
            if(selectedProject.getTypes()[i])
                typeStr += " "+DBManager.projectTypes[i];

        String msg = "مشروعك المختار هو: " +targetProject.getTitle()+"\n" +
                "يتضمن ما يلي: " + typeStr +"\n" +
                "تحت اشراف :" + targetProject.getProf().getName();


        questionArea.setText(msg);
        //dbManager.addProject(targetProject);
    }
}
