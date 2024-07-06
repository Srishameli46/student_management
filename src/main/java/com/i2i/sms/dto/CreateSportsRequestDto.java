package com.i2i.sms.dto;

import java.time.LocalDate;

/**
 * <p>
 * Class representing a sports activity which contains details about the sports
 * such as sport ID,sport name, venue, tutor name.
 * </p>
 */
public class CreateSportsRequestDto {
    private String sportName;
    private String venue;
    private String tutorName;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

}
