/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import com.dream.bizsdk.common.databus.BizData;

import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-12
 * Time: 16:22:46
 */
public abstract class Task extends TimerTask {
    protected String name;
    protected String type;
    protected boolean isPeriodical;
    protected String periodType = "fixed";
    protected long period; //in millisecond;
    protected int year;
    protected int month;
    protected int week;
    protected int day;
    protected int hour;
    protected int minute;
    protected int second;
    protected Date nextRunTime;
    protected Date lastRunTime;
    protected boolean isShceduled;
    protected Date expiresTime;
    protected boolean cancelable;
    protected BizData params;


    protected TaskManager tm;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPeriodical() {
        return this.isPeriodical;
    }

    public void setPeriodical(boolean value) {
        this.isPeriodical = value;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public long getPeriod() {
        return this.period;
    }

    public void setPeriod(long peroid) {
        this.period = peroid;
    }

    public Date getNextRunTime() {
        if (!isPeriodical) {
            return nextRunTime;
        } else {
            if (nextRunTime != null && lastRunTime != null && lastRunTime.getTime() < nextRunTime.getTime()) {
                return nextRunTime;
            } else {
                return computeNextRunTime();
            }
        }
    }

    public void setNextRunTime(Date dt) {
        this.nextRunTime = dt;
        //if this task has not been scheduled, then parse the month, day, hour, minute, second
        //from the nextRunTime;
        if (!isShceduled) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(nextRunTime);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DATE);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            second = cal.get(Calendar.SECOND);
        }
    }

    public Date getLastRunTime() {
        return this.lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public boolean isShceduled() {
        return this.isShceduled;
    }

    public void setScheduled(boolean value) {
        this.isShceduled = value;
    }

    public TaskManager getTaskManager() {
        return this.tm;
    }

    public void setTaskManager(TaskManager tm) {
        this.tm = tm;
    }

    public Date getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public void taskFinished(int retCode) {
        tm.onTaskFinsihed(new TaskFinishedEvent(this.name, new Date(), retCode));
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    private Date computeNextRunTime() {

        Calendar cal = Calendar.getInstance();
        long currentMS = cal.getTime().getTime();

        if (!isPeriodical) {  //if this task is not periodical,then return a expired time;
            cal.add(Calendar.DATE, -1);
        } else {
            if (lastRunTime != null) {
                cal.setTime(lastRunTime);
            }
            //set the time to the begining of the year;
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            cal.add(Calendar.MONTH, (month) * (-1));
            cal.add(Calendar.DATE, (date - 1) * (-1));
            cal.add(Calendar.HOUR_OF_DAY, hour * (-1));
            cal.add(Calendar.MINUTE, minute * (-1));
            cal.add(Calendar.SECOND, second * (-1));

            while (nextRunTime == null || lastRunTime != null && cal.getTime().getTime() <= lastRunTime.getTime()
                    || lastRunTime == null && cal.getTime().getTime() <= currentMS) {
                nextRunTime = cal.getTime();

                if (periodType == null || periodType.compareToIgnoreCase("fixed") == 0) {
                    cal.add(Calendar.MILLISECOND, (int) period);
                } else if (periodType.compareToIgnoreCase("year") == 0) {
                    cal.add(Calendar.YEAR, (int) period);
                } else if (periodType.compareToIgnoreCase("week") == 0) {
                    cal.add(Calendar.WEEK_OF_YEAR, (int) period);
                } else if (periodType.compareToIgnoreCase("month") == 0) {
                    cal.add(Calendar.MONTH, (int) period);
                } else if (periodType.compareToIgnoreCase("day") == 0) {
                    cal.add(Calendar.DATE, (int) period);
                }
            }
        }
        //cal.setTime(nextRunTime);
        if (periodType != null && periodType.compareToIgnoreCase("fixed") != 0) {
            if (periodType.compareToIgnoreCase("year") == 0) {
                if (this.month > 0) {
                    cal.add(Calendar.MONTH, this.month - 1);
                }
                if (day > 0) {
                    cal.add(Calendar.DATE, day - 1);
                } else if (day < 0) {
                    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    days += day;
                    cal.add(Calendar.DATE, days - 1);
                }
                if (hour > 0) {
                    cal.add(Calendar.HOUR_OF_DAY, this.hour);
                }
                if (minute > 0) {
                    cal.add(Calendar.MINUTE, this.minute);
                }
                if (second > 0) {
                    cal.add(Calendar.SECOND, this.second);
                }
            } else if (periodType.compareToIgnoreCase("month") == 0) {
                if (this.month > 0) {
                    cal.add(Calendar.MONTH, this.month - 1);
                }
                if (day > 0) {
                    cal.add(Calendar.DATE, day - 1);
                } else if (day < 0) {
                    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    days += day;
                    cal.add(Calendar.DATE, days - 1);
                }
                if (hour > 0) {
                    cal.add(Calendar.HOUR_OF_DAY, hour);
                }
                if (minute > 0) {
                    cal.add(Calendar.MINUTE, minute);
                }
                if (second > 0) {
                    cal.add(Calendar.SECOND, second);
                }
            } else if (periodType.compareToIgnoreCase("day") == 0 || periodType.compareToIgnoreCase("week") == 0) {
                if (hour > 0) {
                    cal.add(Calendar.HOUR_OF_DAY, hour);
                }
                if (minute > 0) {
                    cal.add(Calendar.MINUTE, minute);
                }
                if (second > 0) {
                    cal.add(Calendar.SECOND, second);
                }
            }
        }
        return cal.getTime();
    }

    public BizData getParams() {
        return params;
    }

    public void setParams(BizData data) {
        this.params = data;
    }
}