package com.i2i.sms.service;

import java.util.List;
import java.util.Optional;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.models.SportsActivity;

/**
 * <p>
 * This sports activity interface represents basic details.
 * Additionally, it stored information about the student's such as name, studentId, grade details and the address detail.
 * Manage the information by the following operation like creating, retrieving and removing the sports activity.
 * </p>
 */
public interface SportsActivityService {
    SportsResponseDto addSport(CreateSportsRequestDto createSportsRequestDto);
    List<SportsResponseDto> getAllSportsActivities();
    boolean removeSportById(String sportId);
    Optional<SportsActivity> getSportDetailsById (String id);
    List<StudentResponseDto> getStudentsInSport(String sportId);
    boolean isSportsActivityExist(String sportId);
    SportsResponseDto updateSports(String id, CreateSportsRequestDto createSportsRequestDto);
}
