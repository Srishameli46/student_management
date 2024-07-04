package com.i2i.sms.dto;

import com.i2i.sms.models.SportsActivity;

import java.util.Date;

public class SportsResponseDto {

    private int sportId;
    private String sportName;
    private String venue;
    private String tutorName;
    private Date startDate;

    public SportsResponseDto(SportsActivity sportsActivity) {
        this.sportId = sportsActivity.getSportId();
        this.sportName = sportsActivity.getSportName();
        this.venue = sportsActivity.getVenue();
        this.tutorName = sportsActivity.getTutorName();
        this.startDate = sportsActivity.getStartDate();
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
