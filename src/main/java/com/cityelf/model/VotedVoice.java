package com.cityelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "voted_polls")
public class VotedVoice {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "poll_id")
  private Poll poll;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "answer_id")
  private PollsAnswer answer;

  public VotedVoice() {
  }

  public VotedVoice(User user, PollsAnswer answer) {
    this.user = user;
    this.answer = answer;
    this.poll = answer.getPoll();
  }

  public long getId() {
    return id;
  }

  public Poll getPoll() {
    return poll;
  }

  public User getUser() {
    return user;
  }

  public PollsAnswer getAnswer() {
    return answer;
  }
}
