package com.cityelf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answer_options")
public class PollsAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "answer")
  private String answer;

  @Column(name = "voted")
  private int voted;

  @ManyToOne
  @JoinColumn(name = "poll_id")
  @JsonIgnore
  private Poll poll;

  public long getId() {
    return id;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public int getVoted() {
    return voted;
  }

  public void setVoted(int voted) {
    this.voted = voted;
  }

  public Poll getPoll() {
    return poll;
  }

  public void setPoll(Poll pollId) {
    this.poll = pollId;
  }

  @Override
  public String toString() {
    return "PollsAnswer{"
        + "id=" + id
        + ", answer='" + answer + '\''
        + ", voted=" + voted
        + ", pollId=" + poll.getId()
        + '}';
  }
}
