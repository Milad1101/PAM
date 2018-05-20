package sample;

import com.sun.istack.internal.NotNull;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    ArrayList<Project> projects;
    File file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    boolean isYoussefFile = false;

     public ExcelReader(String path){

        projects = new ArrayList<>();
        file = new File(path);
        init();
        if(isYoussefFile)
            readYoussefFile();
        else
            readFile();
    }


    public ArrayList<Project> getProjects() {
        return projects;
    }


    private void readFile(){

        String title = "";
        boolean[] dept = new  boolean[3];
        boolean[] types = new boolean[14];
        String prof = "";

        XSSFRow row;
        XSSFCell cell;
        int lastRowNum  = sheet.getLastRowNum();

        for(int i =1 ; i <= lastRowNum ; i++){

            title = "";
            dept = new  boolean[3];
            types = new boolean[14];
            prof = "";

            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for(int j = 0 ; j < lastCellNum;j++){
                cell = row.getCell(j);

                if(j == 0) title = cell.getStringCellValue();

                if(j == 1) fillDept(dept,cell.getStringCellValue().split(","));

                if(j == 2) fillTypes(types,cell.getStringCellValue().split(","));

                if(j == 3) prof = cell.getStringCellValue();
            }
            projects.add(new Project(title,dept,types,prof));

        }




    }


    private void readYoussefFile(){

        String title;
        boolean[] dept;
        boolean[] types;
        String prof;

        XSSFRow row;
        XSSFCell cell;
        int lastRowNum  = sheet.getLastRowNum();


        for(int i =1 ; i <= lastRowNum ; i++){

            title = "";
            dept = new  boolean[3];
            types = new boolean[14];
            prof = "";

            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for(int j = 0 ; j < lastCellNum;j++) {
                cell = row.getCell(j);
                if (cell == null) continue;
                if (j == 0) title = cell.getStringCellValue();

                if (j > 0 && j < 15) {

                    if (cell.getStringCellValue().replaceAll(" ", "").equals("*")) {
                        types[j - 1] = true;
                    } else {
                        types[j - 1] = false;
                    }

                }

                if (j > 14 && j < 18) {
                    if (cell.getStringCellValue().replaceAll(" ", "").equals("*")) {
                        dept[j - 15] = true;
                    } else {
                        dept[j - 15] = false;
                    }
                }


                if (j == 19) prof = cell.getStringCellValue();
            }
            projects.add(new Project(title,dept,types,prof));

        }


    }

    private void fillTypes(boolean[] types, String[] values){

        for (String s : values) {
            for (int i = 0; i < types.length; i++) {

                String [] arr = s.split(" ");
                s ="" ;
                for (String ss : arr){
                    ss = ss.replaceAll(" ","");

                    if(!ss.equals("")){
                        if(s.equals("")){
                            s = ss;
                        }
                        else{
                            s += " "+ss;
                        }
                    }
                }

                 if (s.equals(DBManager.projectTypes[i])) {
                    types[i] = true;
                    break;
                }
            }
        }


    }







    private void fillDept(boolean[] dept,String[] values){

        for(String s : values){
            String [] arr = s.split(" ");
            s ="" ;
            for (String ss : arr){
                ss = ss.replaceAll(" ","");

                if(!ss.equals("")){
                    if(s.equals("")){
                        s = ss;
                    }
                    else{
                        s += " "+ss;
                    }
                }
            }


            if(s.equals("برمجيات"))
                dept[0] = true;


            if(s.equals("شبكات"))
                dept[1] = true;


            if(s.equals("ذكاء صنعي"))
                dept[2] = true;
        }

    }





    private void init(){

        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);
            if(row.getLastCellNum() > 4)
                isYoussefFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
