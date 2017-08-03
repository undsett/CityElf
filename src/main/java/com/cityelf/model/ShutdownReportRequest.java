package com.cityelf.model;

public class ShutdownReportRequest {

  private ShutdownReport shutdownReport;

  private long userId;

  public ShutdownReport getShutdownReport() {
    return shutdownReport;
  }

  public void setShutdownReport(ShutdownReport shutdownReport) {
    this.shutdownReport = shutdownReport;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
