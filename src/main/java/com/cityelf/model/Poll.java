package com.cityelf.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "polls")
public class Poll {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Column(name = "subject")
  private String subject;

  @Column(name = "description")
  private String description;

  @Column(name = "time_of_entry")
  private LocalDateTime timeOfEntry;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "answer_options",
      joinColumns = @JoinColumn(name = "poll_id"),
      inverseJoinColumns = @JoinColumn(name = "id"))
  @Transient
  private List<PollsAnswer> pollsAnswers;

  public Poll(Address address, String subject, String description, List<PollsAnswer> pollsAnswers) {
    this.id = id;
    this.address = address;
    this.subject = subject;
    this.description = description;
    this.timeOfEntry = LocalDateTime.now();
    this.pollsAnswers = pollsAnswers;
  }

  public Poll() {
    this.timeOfEntry = LocalDateTime.now();
  }

  public List<PollsAnswer> getPollsAnswers() {
    return pollsAnswers;
  }

  public void setPollsAnswers(List<PollsAnswer> pollsAnswers) {
    this.pollsAnswers = pollsAnswers;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getTimeOfEntry() {
    return timeOfEntry;
  }

  public void setTimeOfEntry(LocalDateTime localDateTime) {
    this.timeOfEntry = localDateTime;
  }

  @Override
  public String toString() {
    return "Poll{"
        + "id=" + id
        + ", address=" + address.getAddress()
        + ", subject='" + subject + '\''
        + ", description='" + description + '\''
        + ", timeOfEntry=" + timeOfEntry.toString()
        + ", pollsAnswers=" + pollsAnswers
        + '}';
  }
}
