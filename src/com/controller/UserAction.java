package com.controller;

import com.dao.UserDao;
import com.model.User;

import java.util.List;

/**
 * @author zzx
 * 2020/4/29 23:34
 */
public class UserAction {
    public static int countUser(int pageSize){
        int count = UserDao.userCount();
        if(count%pageSize!=0){
            return count/pageSize+1;
        }else{
            return count/pageSize;
        }
    }

    public static Object[][] selTable(String[] colNames, String selItem, String searchText, int pageNo, int pageSize) {
        List<User> list = UserDao.userQuery(selItem,searchText,pageNo,pageSize);
        Object[][] results = new Object[list.size()][colNames.length];
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            results[i][0] = user.getUserName();
            results[i][1] = user.getPassword();
            results[i][2] = user.getPhoneNumber();
        }
        return results;
    }
}

