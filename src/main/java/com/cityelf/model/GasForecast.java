package com.cityelf.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "gas_forecasts")
public class GasForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    long id;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "estimatedStop")
    private LocalDateTime estimatedStop;

    @Column(name = "address_id")
    private long address_id;

    @Column(name = "people_report")
    private boolean people_report;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public GasForecast() {
        start = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        estimatedStop = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        peopleReport = false;
        address = new Address();
    }

    public GasForecast(LocalDateTime start, LocalDateTime estimatedStop, boolean people_report, Address address) {
        this.start = start;
        this.estimatedStop = estimatedStop;
        this.people_report = people_report;
        this.address = address;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEstimatedStop() {
        return estimatedStop;
    }

    public void setEstimatedStop(LocalDateTime estimatedStop) {
        this.estimatedStop = estimatedStop;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }

    public boolean isPeople_report() {
        return people_report;
    }

    public void setPeople_report(boolean people_report) {
        this.people_report = people_report;
    }


}
