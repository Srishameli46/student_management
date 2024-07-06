package com.i2i.sms.dto;

import java.util.List;

/**
 * <p>
 * Class representing a student which contains details about the student
 * such as ID, name, date of birth, associated grade, address and their sportsActivity.
 * </p>
 */
public class CreateStudentSportsRequestDto {
    private List<String> sportIds;

    public List<String> getSportIds() {
        return sportIds;
    }

    public void setSportIds(List<String> sportIds) {
        this.sportIds = sportIds;
    }
}
