package com.peirr.guidedstepstv;

import java.util.Date;

/**
 * Created by kurt on 2016/02/29.
 */
public class Details {

    private String name = "";
    private long dob = 1267438242l;
    private boolean female;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }


    @Override
    public String toString() {
        return "Details{" +
                "name='" + name + '\'' +
                ", dob=" + new Date(dob) +
                ", female=" + female +
                '}';
    }
}
