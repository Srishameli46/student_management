package com.i2i.sms.dto;

import java.time.LocalDate;

import com.i2i.sms.models.SportsActivity;
/**
 * <p>
 * Class representing a sports activity which contains details about the sports
 * such as sport ID,sport name, venue, tutor name.
 * </p>
 */
public class SportsResponseDto {

    private String sportId;
    private String sportName;
    private String venue;
    private String tutorName;

    public SportsResponseDto(SportsActivity sportsActivity) {
        this.sportId = sportsActivity.getSportId();
        this.sportName = sportsActivity.getSportName();
        this.venue = sportsActivity.getVenue();
        this.tutorName = sportsActivity.getTutorName();
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

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
