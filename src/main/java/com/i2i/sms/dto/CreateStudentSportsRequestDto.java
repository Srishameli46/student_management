package com.i2i.sms.dto;

import java.util.List;

public class CreateStudentSportsRequestDto {
    private List<Integer> sportIds;

    public List<Integer> getSportIds() {
        return sportIds;
    }

    public void setSportIds(List<Integer> sportIds) {
        this.sportIds = sportIds;
    }
}
