package com.ua.foxminded.university.services;

import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.dao.impl.*;
import com.ua.foxminded.university.dto.GroupDto;
import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.dto.LessonDto;
import com.ua.foxminded.university.dto.TimeSlotDto;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.*;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.ejb.NoSuchEntityException;
import java.util.ArrayList;
import java.util.List;


@Service
public class TimeSlotServices {

    @Autowired
    private DaoEntityImpl<TimeSlot> timeSlotDao;
    @Autowired
    private DaoEntityImpl<Lesson> lessonDao;
    @Autowired
    private DaoEntityImpl<Lector> lectorDao;
    @Autowired
    private DaoEntityImpl<Faculty> facultyDao;
    @Autowired
    private DaoEntityImpl<Group> groupDao;
    @Autowired
    private ValidatorEntity<TimeSlot> validator;


    private static final Logger logger = LoggerFactory.getLogger(TimeSlotServices.class);

    private static final String MISSING_ID_ERROR_MESSAGE = "Missing id time slot.";
    private static final String NOT_EXIST_ENTITY = "Doesn't exist such time slot";


    private TimeSlotDto getDtoById(Long id) {

        TimeSlot timeSlot = timeSlotDao.getById(id);

        Group group = groupDao.getById(timeSlot.getGroup().getGroupId());
        Lesson lesson = lessonDao.getById(timeSlot.getLesson().getLessonId());
        Lector lector = lectorDao.getById(lesson.getLector().getLectorId());
        Faculty faculty = facultyDao.getById(lector.getFaculty().getFacultyId());

        GroupDto groupDto = new GroupDto();
        groupDto.setGroupId(group.getGroupId());
        groupDto.setName(group.getName());
        groupDto.setFaculty(faculty);

        LectorDto lectorDto = new LectorDto();
        lectorDto.setLectorId(lector.getLectorId());
        lectorDto.setFirstName(lector.getFirstName());
        lectorDto.setLastName(lector.getLastName());
        lectorDto.setFaculty(faculty);

        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setName(lesson.getName());
        lessonDto.setLector(lectorDto);

        TimeSlotDto timeSlotDto = new TimeSlotDto();

        timeSlotDto.setTimeSlotId(timeSlot.getTimeSlotId());
        timeSlotDto.setStartLesson(timeSlot.getStartLesson());
        timeSlotDto.setEndLesson(timeSlot.getEndLesson());
        timeSlotDto.setLessonDto(lessonDto);
        timeSlotDto.setGroupDto(groupDto);
        return timeSlotDto;
    }

    private List<TimeSlotDto> getAllDto() {

        List<TimeSlot> timeSlots = timeSlotDao.getAll();
        List<TimeSlotDto> timeSlotDtos = new ArrayList<>();

        Group group;
        Lesson lesson;
        Lector lector;
        Faculty faculty;

        TimeSlotDto timeSlotDto;
        LessonDto lessonDto;
        LectorDto lectorDto;
        GroupDto groupDto;

        for (TimeSlot timeSlot : timeSlots) {

            group = groupDao.getById(timeSlot.getGroup().getGroupId());
            lesson = lessonDao.getById(timeSlot.getLesson().getLessonId());
            lector = lectorDao.getById(lesson.getLector().getLectorId());
            faculty = facultyDao.getById(lector.getFaculty().getFacultyId());

            groupDto = new GroupDto();
            groupDto.setGroupId(group.getGroupId());
            groupDto.setName(group.getName());
            groupDto.setFaculty(faculty);

            lectorDto = new LectorDto();
            lectorDto.setLectorId(lector.getLectorId());
            lectorDto.setFirstName(lector.getFirstName());
            lectorDto.setLastName(lector.getLastName());
            lectorDto.setFaculty(faculty);

            lessonDto = new LessonDto();
            lessonDto.setLessonId(lesson.getLessonId());
            lessonDto.setName(lesson.getName());
            lessonDto.setLector(lectorDto);

            timeSlotDto = new TimeSlotDto();
            timeSlotDto.setTimeSlotId(timeSlot.getTimeSlotId());
            timeSlotDto.setStartLesson(timeSlot.getStartLesson());
            timeSlotDto.setEndLesson(timeSlot.getEndLesson());
            timeSlotDto.setLessonDto(lessonDto);
            timeSlotDto.setGroupDto(groupDto);

            timeSlotDtos.add(timeSlotDto);
        }

        return timeSlotDtos;
    }

    public List<TimeSlotDto> getAll() {
        logger.debug("Trying to get all time slots");
        try {
            return getAllDto();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Time slots is not exist");
            throw new NoSuchEntityException("Doesn't exist such time slots");
        } catch (DataAccessException e) {
            logger.error("Failed to get all time slots", e);
            throw new ServiceException("Failed to get list of time slots", e);
        }
    }

    public TimeSlotDto getById(long id) {
        logger.debug("Trying to get time slot with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        TimeSlotDto timeSlot;
        try {
            timeSlot = getDtoById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve time slot with id={}", id, e);
            throw new ServiceException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public TimeSlot getByIdLight(long id) {
        logger.debug("Trying to get time slot with id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        TimeSlot timeSlot;
        try {
            timeSlot = timeSlotDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve time slot with id={}", id, e);
            throw new ServiceException("Failed to retrieve time slot by id", e);
        }
        return timeSlot;
    }

    public List<TimeSlot> getAllLight() {
        logger.debug("Trying to get all time slots");
        try {
            return timeSlotDao.getAll();
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Time slots is not exist");
            throw new NoSuchEntityException("Doesn't exist such time slots");
        } catch (DataAccessException e) {
            logger.error("Failed to get all time slots", e);
            throw new ServiceException("Failed to get list of time slots", e);
        }
    }

    public void create(TimeSlot timeSlot) {
        logger.debug("Trying to create time slot: {}", timeSlot);

        validator.validate(timeSlot);
        try {
             timeSlotDao.create(timeSlot);
        } catch (DataAccessException e) {
            logger.error("Failed to create time slot: {}", timeSlot, e);
            throw new ServiceException("Failed to create time slot", e);
        }
    }

    public void deleteById(long id) {
        logger.debug("Trying to delete time slot id={}", id);

        if (id == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
             timeSlotDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot with id={}", id);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete time slot with id={}", id, e);
            throw new ServiceException("Failed to delete time slot by id", e);
        }
    }
    public void delete(TimeSlot timeSlot) {
        logger.debug("Trying to delete time slot ={}", timeSlot);

        if (timeSlot == null) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        try {
            timeSlotDao.delete(timeSlot);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot ={}", timeSlot);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to delete time slot ={}", timeSlot, e);
            throw new ServiceException("Failed to delete time slot ", e);
        }
    }

    public void update(TimeSlot timeSlot) {
        logger.debug("Trying to update time slot: {}", timeSlot);

        if (timeSlot.getTimeSlotId() == 0) {
            logger.warn(MISSING_ID_ERROR_MESSAGE);
            throw new ServiceException(MISSING_ID_ERROR_MESSAGE);
        }
        validator.validate(timeSlot);
        try {
            timeSlotDao.getById(timeSlot.getTimeSlotId());
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not existing time slot: {}", timeSlot);
            throw new NoSuchEntityException(NOT_EXIST_ENTITY);
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve time slot: {}", timeSlot, e);
            throw new ServiceException("Failed to retrieve time slot:", e);
        }
        try {
             timeSlotDao.update(timeSlot);
        } catch (DataAccessException e) {
            logger.error("Failed to update time slot: {}", timeSlot, e);
            throw new ServiceException("Problem with updating time slot");
        }
    }
}

