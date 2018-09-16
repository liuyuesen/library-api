package librarysystem.libraryapi.controller.Manager;

import librarysystem.libraryapi.DBManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
@Controller
@RequestMapping("/manager")
public class ManagerDetailController {
    String sql = "SELECT * FROM book";
    DBManager dbManager = new DBManager(sql);
    ResultSet result = null;

    @RequestMapping("/detail")
    public String ManageDetail(Model model){
        return "manager/detail";
    }
}
