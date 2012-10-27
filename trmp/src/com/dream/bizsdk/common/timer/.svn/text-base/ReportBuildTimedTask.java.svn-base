/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import com.dream.bizsdk.common.report.Engine;
import com.dream.bizsdk.common.report.Report;
import com.dream.bizsdk.common.databus.BizData;

import java.util.Calendar;
import java.util.Date;
import java.rmi.RemoteException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-28
 * Time: 14:02:35
 */
public class ReportBuildTimedTask extends GenericTask {
    public void run() {
        Engine reportEngine = new Engine(context);
        String templateName = params.getString("templateName");

        //add system defined parameters;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        int week_of_month = cal.get(Calendar.WEEK_OF_MONTH);
        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        params.add("year", year);
        params.add("month", month);
        params.add("date", date);
        params.add("week_of_month", week_of_month);
        params.add("week_of_year,", week_of_year);
        params.add("hour", hour);
        params.add("minute", minute);
        params.add("second", second);

        Report r = reportEngine.buildReport(templateName, params);

        //send the envet to the context;
        if (r != null) {
            String path = r.getPath();
            BizData d = new BizData();
            d.add("reportPath", path);
            d.add("templateName", templateName);
            try {
                tm.onTaskFinsihed(new TaskFinishedEvent(this.getName(), new Date(), 0));
                context.notifyEvent("report_built", d);
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        }
    }
}