/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.timer;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.databus.Util;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.text.ParseException;

import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * Description:
 * 定时任务分两种，一是周期性的，另一是一次性的。 每个定时任务都需要在注册文件中
 * 进行注册，系统在启动的时候，会读这个文件，并初始化定时器和定时任务。
 * <p/>
 * 当一个定时任务成功完成后（对于周期性任务则为已经到期，不需要再循环了），系统会
 * 将这个定时任务从注册文件中删除。
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-12
 * Time: 16:22:04
 */
public class TaskManager implements TimerTaskEventListener {

    private IBLContext context;
    private HashMap tasks = new HashMap();
    private Hashtable cancelableTimers = new Hashtable();
    private Timer timer = new Timer(true);
    private XMLFile registerFile;
    private ServletContext sc;

    private Logger log;

    public TaskManager() {
    }

    public TaskManager(ServletContext sc) {
        this.sc = sc;
    }

    public TaskManager(IBLContext context) {
        this.context = context;
    }

    public IBLContext getContext() {
        return this.context;
    }

    public void setContext(IBLContext context) {
        this.context = context;
    }

    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }

    public ServletContext getServletContext() {
        return this.sc;
    }

    public void init(String path) throws TimerTaskInitializtionException {
        init(new File(path));
    }

    public void init(File file) throws TimerTaskInitializtionException {
        int count = 0;

        //get the logger for this object;
        log = Logger.getLogger(TaskManager.class);

        //open the regiester file;
        try {
            registerFile = new XMLFile(file);
        } catch (ParserConfigurationException e) {
            throw new TimerTaskInitializtionException(e);
        } catch (SAXException e) {
            throw new TimerTaskInitializtionException(e);
        } catch (IOException e) {
            throw new TimerTaskInitializtionException(e);
        }

        //begin to read the init file;
        NodeList nl = registerFile.selectNodeList("/tasks/task");
        count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Task t = buildTaskFromNode((Element) nl.item(i));
            if (t != null) {
                tasks.put(t.getName(), t);
                scheduleTask(t);
            }
        }
    }

    /**
     * API to add a timed task to the register file from program;
     * this method doest not schedule this timed task to the timer;
     *
     * @param t the new Task to be added;
     */
    public synchronized void addTask(Task t) {

        t.setTaskManager(this);

        tasks.put(t.getName(), t);

        //add this task to the register file;
        if (t instanceof BLTask) {
            addBLTask2RegisterFile((BLTask) t);
        } else if (t instanceof GenericTask) {
            addGenericTask2RegisterFile((GenericTask) t);
        }
    }

    /**
     * APT to add a timed task to the register file, and shcedule this task
     * to timer at the same time;
     *
     * @param t the Task to be added and scheduled.
     */
    public synchronized void addAndShceduleTask(Task t) {
        tasks.put(t.getName(), t);

        //add this task to the register file;
        if (t instanceof BLTask) {
            addBLTask2RegisterFile((BLTask) t);
        }
        scheduleTask(t);
    }

    /**
     * delete a task from the register file.
     *
     * @param t
     */
    public void delTask(Task t) {
        if (t != null) {
            delTask(t.getName());
        }
    }

    public synchronized void delTask(String name) {
        //remove from the Task Map;
        Task t = (Task) tasks.remove(name);

        //remove from the register file;
        Node n = registerFile.selectSingleNode("/tasks/task[@name=\"" + name + "\"]");
        if (n != null) {
            Node p = n.getParentNode();
            p.removeChild(n);

            try {
                registerFile.save();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //cancel and release the matched timer if this task is a cancelable task
        if (t.isCancelable()) {
            Timer cancelableTimer = (Timer) cancelableTimers.remove(name);
            if (cancelableTimer != null) {
                cancelableTimer.cancel();
            }
        }
    }

    /**
     * schedule a task to a timer;
     *
     * @param t the task to be created;
     */
    public void scheduleTask(Task t) {
        Date d = t.getNextRunTime();
        String name = t.getName();
        String periodType = t.getPeriodType();

        try {
            //固定周期；
            if (t.isPeriodical() && (periodType == null || periodType.compareToIgnoreCase("fixed") == 0)) {
                if (t.isCancelable()) {
                    Timer newTimer = new Timer();
                    newTimer.schedule(t, d.getTime() - System.currentTimeMillis(), t.getPeriod());
                    cancelableTimers.put(name, t);
                } else {
                    timer.schedule(t, d.getTime() - System.currentTimeMillis(), t.getPeriod());
                }
            } else { //非周期性任务，或者变长周期任务；
                //if a Task is cancelable, then a Timer only for this task's use will be created;
                if (t.isCancelable()) {
                    if (!t.isShceduled()) {   //new Timer should be created;
                        Timer newTimer = new Timer();
                        newTimer.schedule(t, d.getTime() - System.currentTimeMillis());
                        cancelableTimers.put(t.getName(), t);
                    } else {  //the Task has been scheduled, so the cancelable timer should have been created;
                        Timer cancelableTimer = (Timer) cancelableTimers.get(name);
                        cancelableTimer.schedule(t, d.getTime() - System.currentTimeMillis());
                    }
                } else {  //otherwise, the shared timer is used;
                    timer.schedule(t, d.getTime() - System.currentTimeMillis());
                }
            }

            //write info log;
            if (log.isInfoEnabled()) {
                log.info("Timed task:" + name + " scheduled at " + BizData.sdfTime.format(d));
            }
        } catch (IllegalArgumentException iae) {
            log.error("illeagle argument when scheduling timed task: " + name + " at "
                    + BizData.sdfTime.format(d) + " with period " + t.getPeriod() + " " + t.getPeriodType());
        } catch (IllegalStateException ise) {
            log.error("illeagle state when scheduling timed task:" + name);
        }

        t.setScheduled(true);
    }

    /**
     * User should call this method before shutdown the application;
     */
    public void shutdown() {
        //cancel the timer;
        timer.cancel();

        ///cancel all the cancelable timers;
        Iterator it = cancelableTimers.values().iterator();
        while (it.hasNext()) {
            Timer t = (Timer) it.next();
            t.cancel();
        }
        cancelableTimers.clear();

        //clear tasks;
        tasks.clear();

        //close(save) the regiester file;
        try {
            registerFile.close();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * called by the timer thread when a task is finished;
     *
     * @param tfe the event;
     */
    public void onTaskFinsihed(TaskFinishedEvent tfe) {
        //if a task is finished  successfully, then it will not be executed any one
        //and will be removed from the register file;
        String name = tfe.getTaskName();
        Task t = (Task) tasks.get(name);
        //write log;
        if (log.isInfoEnabled()) {
            log.info("Timed task:" + name + " finished with return code: " + tfe.getRetCode());
        }

        if (!t.isPeriodical()) {  //if this task is not a periodical task, then delete it directly;
            delTask(name);
        } else {
            //check if the perodical task has expires;
            Date expiresTime = t.getExpiresTime();
            if (expiresTime != null) {
                if (expiresTime.getTime() < System.currentTimeMillis() + t.getPeriod()) {    //does expires;
                    delTask(name);
                }
            } else {
                String periodType = t.getPeriodType();
                //if the period is variable length, then reschedule this task;
                if (periodType != null && periodType.compareToIgnoreCase("fixed") != 0) {
                    scheduleTask(t);
                }
            }
        }
    }

    /**
     * @param n
     * @return
     */
    private Task buildTaskFromNode(Element n) {

        Task t = null;

        String type = registerFile.getSingleNodeValue(n, "type");

        if (type == null || type.compareToIgnoreCase("bl") == 0) {    //BLTask
            t = buildBLTaskFromNode(n);
        } else if (type.compareToIgnoreCase("generic") == 0) {
            t = buildGenericTaskFromNode(n);
        }

        return t;
    }

    private BLTask buildBLTaskFromNode(Element n) {
        String value = null;
        BLTask t = new BLTask();

        //build common part of the node;
        buildTaskFromNode(t, n);

        value = registerFile.getSingleNodeValue(n, "run/className");
        if (value != null && value.length() > 0) {
            t.setClassName(value);
        }

        value = registerFile.getSingleNodeValue(n, "run/methodName");
        if (value != null && value.length() > 0) {
            t.setMethodName(value);
        }

        return t;
    }

    /**
     * @param n
     * @return
     */
    private GenericTask buildGenericTaskFromNode(Element n) {
        String value = null;
        GenericTask t = null;
        value = registerFile.getSingleNodeValue(n, "run/className");
        if (value != null && value.length() > 0) {
            try {
                t = (GenericTask) Class.forName(value).newInstance();
            } catch (ClassNotFoundException e) {
                log.error("Generic task class " + value + " was not found.", e);
                return null;
            } catch (InstantiationException e) {
                log.error("can't create object of class " + value, e);
                return null;
            } catch (IllegalAccessException e) {
                log.error("can't access method of class " + value, e);
                return null;
            } catch (ClassCastException e) {
                log.error("class " + value + " must be a subclass of " + GenericTask.class.getName(), e);
                return null;
            }
        } else {
            if (log.isEnabledFor(Priority.WARN)) {
                log.warn("property class of generic timed task was not defined.");
            }
            return null;
        }

        buildTaskFromNode(t, n);

        //set the context to the GenericTask;
        t.setContext(context);
        t.setServletContext(sc);

        return t;
    }

    /**
     * build the common part of a timed task from the node;
     *
     * @param t
     * @param n
     */
    protected void buildTaskFromNode(Task t, Element n) {
        String value = null;
        t.setName(registerFile.getSingleNodeValue(n, "@name"));
        //time set directly;
        value = registerFile.getSingleNodeValue(n, "timer/nextRunTime");
        if (value != null && value.length() > 0) {
            try {
                t.setNextRunTime(BizData.sdfTime.parse(value));
            } catch (ParseException pe) {
                log.error("next run time of task: " + t.getName() + " is not a valid time.", pe);
            }
        }

        //year;
        value = registerFile.getSingleNodeValue(n, "timer/year");
        if (value != null && value.length() > 0) {
            t.setYear(Integer.parseInt(value));
        } else {
            t.setYear(0);
        }

        //month;
        value = registerFile.getSingleNodeValue(n, "timer/month");
        if (value != null && value.length() > 0) {
            t.setMonth(Integer.parseInt(value));
        } else {
            t.setMonth(0);
        }

        //day;
        value = registerFile.getSingleNodeValue(n, "timer/day");
        if (value != null && value.length() > 0) {
            t.setDay(Integer.parseInt(value));
        } else {
            t.setDay(0);
        }

        //hour;
        value = registerFile.getSingleNodeValue(n, "timer/hour");
        if (value != null && value.length() > 0) {
            t.setHour(Integer.parseInt(value));
        } else {
            t.setHour(0);
        }

        //minute;
        value = registerFile.getSingleNodeValue(n, "timer/minute");
        if (value != null && value.length() > 0) {
            t.setMinute(Integer.parseInt(value));
        } else {
            t.setMinute(0);
        }

        //second;
        value = registerFile.getSingleNodeValue(n, "timer/second");
        if (value != null && value.length() > 0) {
            t.setSecond(Integer.parseInt(value));
        } else {
            t.setSecond(0);
        }

        //isPeriodical;
        value = registerFile.getSingleNodeValue(n, "timer/isPeriodical");
        if (value != null && value.length() > 0 && value.compareToIgnoreCase("true") == 0) {
            t.setPeriodical(true);
        } else {
            t.setPeriodical(false);
        }

        value = registerFile.getSingleNodeValue(n, "timer/periodType");
        if (value != null && value.length() > 0) {
            t.setPeriodType(value);
        }

        value = registerFile.getSingleNodeValue(n, "timer/cancelable");
        if (value != null && value.length() > 0) {
            t.setCancelable(true);
        }

        value = registerFile.getSingleNodeValue(n, "timer/period");
        if (value != null && value.length() > 0) {
            t.setPeriod(Long.parseLong(value));
        } else {
            t.setPeriod(0);
        }

        //parse and set the Task's Parameters;
        t.setParams(buildTaskParamsFromNode(n));
        t.setTaskManager(this);
    }


    /**
     * @param e
     * @return BizData object, never null;
     */
    private BizData buildTaskParamsFromNode(Element e) {
        int count = 0;
        BizData d = new BizData();

        Element paramNode = (Element) registerFile.selectSingleNode(e, "params");
        if (paramNode != null) {
            NodeList nl = registerFile.selectNodeList(paramNode, "data");
            count = nl.getLength();
            for (int i = 0; i < count; i++) {
                buildDataFromNode(d, (Element) nl.item(i));
            }
        }

        return d;
    }

    private void buildDataFromNode(BizData d, Element e) {
        String name = e.getAttribute("name");
        String field = e.getAttribute("field");
        String row = e.getAttribute("row");
        String attr = e.getAttribute("attr");
        String value = e.getAttribute("value");
        if (name.length() < 1) {    //name can't be empty;
            return;
        }
        if (field.length() < 1) {
            if (row.length() < 1) {
                if (attr.length() < 1) {
                    d.add(name, value);
                } else {
                    d.addAttr(name, attr, value);
                }
            } else {
                d.add(name, Integer.parseInt(row), value);
            }
        } else {
            if (row.length() < 1) { //default row to 0;
                row = "0";
            }

            if (attr.length() < 1) {
                d.add(name, field, row, value);
            } else {
                d.addAttr(name, field, row, attr, value);
            }
        }
    }

    /**
     * @param t
     */
    private void addBLTask2RegisterFile(BLTask t) {
        String name = t.getName();
        //class name and method name;
        String className = t.getClassName();
        String methodName = t.getMethodName();

        //timer settings;
        int timerYear = t.getYear();
        int timerMonth = t.getMonth();
        int timerDay = t.getDay();
        int timerHour = t.getHour();
        int timerMinute = t.getMinute();
        int timerSecond = t.getSecond();

        //params settings;
        BizData d = t.getParams();
        Element e = registerFile.appendChild("task");
        e.setAttribute("name", name);
        Element run = registerFile.appendChild(e, "run");
        registerFile.appendChild(run, "className", className);
        registerFile.appendChild(run, "methodName", methodName);
        Element timer = registerFile.appendChild(e, "timer");
        registerFile.appendChild(timer, "year", Integer.toString(timerYear));
        registerFile.appendChild(timer, "month", Integer.toString(timerMonth));
        registerFile.appendChild(timer, "day", Integer.toString(timerDay));
        registerFile.appendChild(timer, "hour", Integer.toString(timerHour));
        registerFile.appendChild(timer, "minute", Integer.toString(timerMinute));
        registerFile.appendChild(timer, "second", Integer.toString(timerSecond));
        Element params = registerFile.appendChild(e, "params");
        if (d != null) {
            Util.toXML(d, params);
        }
    }

    /**
     * @param t
     */
    private void addGenericTask2RegisterFile(GenericTask t) {
        String name = t.getName();
        //class name and method name;
        String className = t.getClassName();

        //timer settings;
        int timerYear = t.getYear();
        int timerMonth = t.getMonth();
        int timerDay = t.getDay();
        int timerHour = t.getHour();
        int timerMinute = t.getMinute();
        int timerSecond = t.getSecond();

        //params settings;
        BizData d = t.getParams();
        Element e = registerFile.appendChild("task");
        e.setAttribute("name", name);
        Element run = registerFile.appendChild(e, "run");
        registerFile.appendChild(run, "className", className);
        Element timer = registerFile.appendChild(e, "timer");
        registerFile.appendChild(timer, "year", Integer.toString(timerYear));
        registerFile.appendChild(timer, "month", Integer.toString(timerMonth));
        registerFile.appendChild(timer, "day", Integer.toString(timerDay));
        registerFile.appendChild(timer, "hour", Integer.toString(timerHour));
        registerFile.appendChild(timer, "minute", Integer.toString(timerMinute));
        registerFile.appendChild(timer, "second", Integer.toString(timerSecond));
        Element params = registerFile.appendChild(e, "params");
        if (d != null) {
            Util.toXML(d, params);
        }
    }
}