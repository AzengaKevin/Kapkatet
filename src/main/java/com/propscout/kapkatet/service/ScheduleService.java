package com.propscout.kapkatet.service;

import com.propscout.kapkatet.model.ScheduleItem;
import com.propscout.kapkatet.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<ScheduleItem> getAllItems() {

        return scheduleRepository.findAll();

    }

    public ScheduleItem addScheduleItem(ScheduleItem scheduleItem) throws Exception {

        //Check whether a schedule item exists
        if (scheduleItem.getId() != null && scheduleRepository.existsById(scheduleItem.getId())) {
            throw new Exception("A schedule item exists with the same id");
        }

        //Validate User Inputs
        validateInputs(scheduleItem);

        //Check if center already added
        checkWhetherCenterIsAssigned(scheduleItem);

        return scheduleRepository.save(scheduleItem);
    }

    public List<ScheduleItem> findByUserId(long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    private void validateInputs(ScheduleItem scheduleItem) throws Exception {

        if (scheduleItem.getUserId() == null) {

            throw new Exception("Select a clerk to assigned the center");
        }

        if (scheduleItem.getCenterId() == null) {

            throw new Exception("Select a center to be assigned to the clerk");

        }

        if (scheduleItem.getExpectedTransactionTime() == null) {

            throw new Exception("Approximate arrival time of the clerk to the center is not specified");

        }

        if (scheduleItem.getWeekdays() == null) {

            throw new Exception("Transactions days cannot be null");

        }
    }

    private void checkWhetherCenterIsAssigned(ScheduleItem scheduleItem) throws Exception {

        if (scheduleRepository.findByCenterId(scheduleItem.getCenterId()).size() > 0) {
            throw new Exception("The center is already assigned to someone else");
        }
    }
}
