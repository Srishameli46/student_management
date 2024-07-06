package com.i2i.sms.service;

import java.util.List;

import com.i2i.sms.dto.CreateStudentRequestDto;
import com.i2i.sms.dto.CreateStudentSportsRequestDto;
import com.i2i.sms.dto.SportsResponseDto;
import com.i2i.sms.dto.StudentResponseDto;
import com.i2i.sms.dto.StudentWithAllDetailsDto;

/**
 * <p>
 * This student interface represents basic details.
 * Additionally, it stored information about the student's grade and address details.
 * Manage the information by the following operation like creating, retrieving and removing the students.
 * </p>
 */
public interface StudentService {
    StudentResponseDto addStudent(CreateStudentRequestDto createStudentRequestDto);
    List<StudentResponseDto> getAllStudents();
    StudentWithAllDetailsDto searchStudentById(String id);
    boolean removeStudentById(String id);
    StudentResponseDto updateStudent(String id, CreateStudentRequestDto createStudentRequestDto);
   List<SportsResponseDto> addStudentToSportActivity(String studentId, CreateStudentSportsRequestDto createStudentSportsRequestDto);

    }
