package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.CreateSportsRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;

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
    boolean removeSportById(int sportId);
    List<SportsResponseDto> addStudentToSportActivity(int studentId, CreateStudentSportsRequestDto createStudentSportsRequestDto);
    List<StudentResponseDto> getStudentsInSport(int sportId);

}
