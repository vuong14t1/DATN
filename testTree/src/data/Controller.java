package data;

import com.google.gson.Gson;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private static List<Node> dataNode = new ArrayList<>();
    private DataTree dataTree = new DataTree();
    private Gson gson = new Gson();
    private static Controller instance;
    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void loadDataNode () {
        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(new File("C:\\Users\\vuong_000\\IdeaProjects\\testTree\\src\\inputExcel\\data.xls"));
            Sheet sheet = workbook.getSheet("Sheet1");
            int rows =  sheet.getRows();
//            int cols = sheet.getColumns();
            for (int row = 1; row < rows; row++) {
                String id = sheet.getCell(0, row).getContents();
                String pathKey = sheet.getCell(1, row).getContents();
                String idParent = sheet.getCell(2, row).getContents();
                String idMonther = sheet.getCell(3, row).getContents();
                String liftIndex = sheet.getCell(4, row).getContents();
                String name = sheet.getCell(5, row).getContents();
                String relation = sheet.getCell(6, row).getContents();
                Node node = new Node(id, pathKey, idParent, idMonther,Integer.parseInt(liftIndex), name, Integer.parseInt(relation + ""));
                dataNode.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateTree () {
        for (Node c: dataNode) {
            dataTree.addNode(c);
//            System.out.println(c);
        }
        System.out.println("data " + gson.toJson(dataTree.getRootNode()));
    }
}
