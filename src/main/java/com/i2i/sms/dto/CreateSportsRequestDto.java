package com.i2i.sms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


/**
 * <p>
 * Class representing a sports activity which contains details about the sports
 * such as sport name, venue, tutor name.
 * </p>
 */
public class CreateSportsRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Sports name should contain only letters and space")
    private String sportName;

    @NotBlank
    private String venue;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Tutor name should contain only letters and space")
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
