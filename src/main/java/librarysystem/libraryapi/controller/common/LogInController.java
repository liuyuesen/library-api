package librarysystem.libraryapi.controller.common;

import librarysystem.libraryapi.Bean.Manager;
import librarysystem.libraryapi.Bean.User;
import librarysystem.libraryapi.controller.tool.DBManager;
import librarysystem.libraryapi.controller.tool.ErrorAlert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

@Controller
@RequestMapping()
public class LogInController {
    private int type;
    private String userName;
    private String password;

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {
        return "/login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        userName = httpServletRequest.getParameter("userName");
        password = httpServletRequest.getParameter("password");
        type = Integer.parseInt(httpServletRequest.getParameter("user_type"));
        if (type == 0) {
            //学生
            String sql = "SELECT password FROM user where user_id = " + userName + ";";
            DBManager dbManager = new DBManager(sql);
            ResultSet result;

            try {
                String DBpassword = null;
                result = dbManager.preparedStatement.executeQuery();
                while (result.next()) {
                    DBpassword = result.getString("password");
                }
                if (DBpassword.equals(password)) {
                    result.close();
                    dbManager.close();
                    getUserDetail(userName, 0);
                    return "redirect:/student/detail";
                } else {//这还缺少一个提醒账号密码错误
                    ErrorAlert.popAlert(response,"密码错误");
                }
            } catch (Exception e) {
                ErrorAlert.popAlert(response,"账号或账号类型错误");
            }
        } else if (type == 1){
            String sql = "SELECT password FROM manager where user_id = " + userName + ";";
            DBManager dbManager = new DBManager(sql);
            ResultSet result = null;
            try {
                String DBpassword = null;
                result = dbManager.preparedStatement.executeQuery();
                while (result.next()) {
                    DBpassword = result.getString("password");
                }
                if (DBpassword.equals(password)) {
                    result.close();
                    dbManager.close();
                    getUserDetail(userName, 1);
                    return "redirect:/manager/detail";
                } else {
                    ErrorAlert.popAlert(response,"密码错误");
                }

            } catch (Exception e) {
                ErrorAlert.popAlert(response,"账号或账号类型错误");
            }
        }else {
            ErrorAlert.popAlert(response,"未输入用户类型");
        }
        return "/login";
    }

    //当发生意外的时候弹出提

    //当验证用户账号密码成功的时候把用户的个人信息存储下来
    private void getUserDetail(String userName, int type) {
        if (type == 0) {
            String sql = "SELECT * FROM user where user_id = " + userName + ";";
            DBManager dbManager = new DBManager(sql);
            ResultSet result = null;
            try {
                result = dbManager.preparedStatement.executeQuery();
                while (result.next()) {
                    User.getInstance().setName(result.getString("name"))
                            .setUserID(result.getString("user_id"))
                            .setPassword(result.getString("password"))
                            .setPhone(result.getString("phone"))
                            .setAcademy(result.getString("academy"))
                            .setMajor(result.getString("major"))
                            .setGrade(Integer.parseInt(result.getString("grade")))
                            .setSex(Integer.parseInt(result.getString("sex")))
                            .setCard(Integer.parseInt(result.getString("card")))
                            .setCredit(Integer.parseInt(result.getString("credit")));
                }
                result.close();
                dbManager.close();
            } catch (Exception e) {

            }
        } else {
            String sql = "SELECT * FROM manager where user_id = " + userName + ";";
            DBManager dbManager = new DBManager(sql);
            ResultSet result = null;
            try {
                result = dbManager.preparedStatement.executeQuery();
                while (result.next()) {
                    Manager.getInstance().setName(result.getString("name"))
                            .setUserID(result.getString("user_id"))
                            .setPassword(result.getString("password"))
                            .setWorkID(result.getString("work_id"));
                }
                result.close();
                dbManager.close();
            } catch (Exception e) {

            }
        }

    }
}
