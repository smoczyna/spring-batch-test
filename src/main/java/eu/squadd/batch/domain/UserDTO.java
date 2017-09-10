/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author smorcja
 */
public class UserDTO {

    private int userid;
    private String name;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        //return "UserDTO{" + "userid=" + userid + ", name=" + name + '}';
        return new ToStringBuilder(this)
                .append("userid", this.userid)
                .append("name", this.name)
                .toString();
    }

}
