package com.cityelf.utils.voting.utils;

import com.cityelf.model.PollsAnswer;

public class VoteResult {

  private PollsAnswer answer;

  private int voices;

  private Double percents;

  public VoteResult(PollsAnswer answer, double totalVotes) {
    this.answer = answer;
    this.voices = answer.getVoted();
    this.setPercents(totalVotes);
  }

  public PollsAnswer getAnswer() {
    return answer;
  }

  public int getVoices() {
    return voices;
  }

  public double getPercents() {
    return percents;
  }

  private void setAnswer(PollsAnswer answer) {
    this.answer = answer;
  }

  public void setVoices(int voices) {
    this.voices = voices;
  }

  private void setPercents(double totalVotes) {
    this.percents = Math.round(answer.getVoted() / totalVotes * 10_000) / 100d;
  }
}
