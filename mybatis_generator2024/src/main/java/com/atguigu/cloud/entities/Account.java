package com.atguigu.cloud.entities;

import javax.persistence.*;

/**
 * 表名：t_account
*/
@Table(name = "t_account")
public class Account {
    private String username;

    private Integer money;

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(Integer money) {
        this.money = money;
    }
}