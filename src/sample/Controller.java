package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements Initializable{

    @FXML
    ListView<String> projectList1 , projectList2 , professorList1,professorList2;
    @FXML
    Button yesAnswerBut , noAnswerBut , selectProjectBut1 , selectProjectBut2
            ,selectProfessorBut1 , selectProfessorBut2;

    DBManager dbManager;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbManager = new DBManager();
    }




    public void yesButClicked(ActionEvent event) {



    }



    private void newMethod(Project project){

        ExcelReader er = new ExcelReader("astb.xlsx");

        KnnAlgorithm knnAlgorithm = new KnnAlgorithm(er.getProjects());


        ArrayList<Project> myProjects = knnAlgorithm.getGoodProjects(3,new ProjectPoint(project));

        for(Project p : myProjects){
            if((p.getDept()[0]&&project.getDept()[0])||(p.getDept()[1]&&project.getDept()[1])||(p.getDept()[2]&&project.getDept()[2]))
                projectList1.getItems().add(p.getTitle());
        }

    }


}
