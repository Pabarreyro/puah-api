package dao;

import models.Report;
import org.sql2o.Sql2o;

public class Sql2oReportDao implements ReportDao{
    private final Sql2o sql2o;

    public Sql2oReportDao(Sql2o sql2o) { this.sql2o = sql2o; }
}
