package com.propscout.kapkatet.model;

import javax.persistence.*;

@Entity(name = "ScheduleItem")
@Table(name = "schedule")
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "center_id")
    private Long centerId;

    @Column(name = "expected_transaction_time")
    private String expectedTransactionTime;

    private String weekdays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public String getExpectedTransactionTime() {
        return expectedTransactionTime;
    }

    public void setExpectedTransactionTime(String expectedTransactionTime) {
        this.expectedTransactionTime = expectedTransactionTime;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    @Override
    public String toString() {
        return "ScheduleItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", centerId=" + centerId +
                ", expectedTransactionTime='" + expectedTransactionTime + '\'' +
                ", weekdays='" + weekdays + '\'' +
                '}';
    }
}
