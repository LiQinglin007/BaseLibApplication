package com.lixiaomi.baselibapplication.bean;

/**
 * Description: <br>
 * User: dell - XiaomiLi<br>
 * Date: 2018-06-30<br>
 * Time: 17:30<br>
 * UpdateDescription：<br>
 */
public class AppController {
    private String name;
    private String sex;

    public AppController(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
