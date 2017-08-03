package com.cityelf.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reports_to_users")
public class UserReports {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "report_id")
  private ShutdownReport shutdownReport;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public UserReports() {
  }

  public UserReports(ShutdownReport shutdownReport, User user) {
    this.shutdownReport = shutdownReport;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public ShutdownReport getShutdownReport() {
    return shutdownReport;
  }

  public void setShutdownReport(ShutdownReport shutdownReport) {
    this.shutdownReport = shutdownReport;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof UserReports)) {
      return false;
    }
    UserReports that = (UserReports) obj;
    return getId() == that.getId()
        && Objects.equals(getShutdownReport(), that.getShutdownReport())
        && Objects.equals(getUser(), that.getUser());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getShutdownReport(), getUser());
  }

  @Override
  public String toString() {
    return "UserReports{"
        + "id=" + id
        + ", shutdownReport=" + shutdownReport
        + ", user=" + user + '}';
  }
}
