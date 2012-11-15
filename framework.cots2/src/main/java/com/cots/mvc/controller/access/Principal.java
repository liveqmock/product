/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

import java.util.HashSet;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-26
 * Time: 13:48:50
 * Version: 1.0
 */
public abstract class Principal {

    protected String id;
    protected HashSet subjects;

    public Principal() {
    }

    public Principal(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * add a Subject to this Principal.
     * only be called after the static subjects of the Pincipal have
     * been initialized.otherwise NullPointerException will be thrown.
     *
     * @param subject
     */
    public synchronized  void addSubject(Subject subject){
        subjects.add(subject.getId());
    }

    /**
     * add a subjects list to this pricipal;
     * only be called after the static subjects of the Pincipal have
     * been initialized. otherwise NullPointerException will be thrown.
     *
     * @param subjects a list of subjects or subject IDs;
     */
    public synchronized void addSubjects(ArrayList subjects){
        int count = subjects.size();
        for(int i=0;i<count;i++){
            Object o = subjects.get(i);
            if(o instanceof Subject){
                addSubject(((Subject)o).getId());
            }else if(o instanceof String){
                this.subjects.add((String)o);
            }
        }
    }


    /**
     *
     * @param subjectID
     */
    public synchronized void addSubject(String subjectID){
        subjects.add(subjectID);
    }

    /**
     *
     * @param subjectID
     * @return
     */
    public boolean containsSubject(String subjectID){
        return subjects.contains(subjectID);
    }

    /**
     * check if the principal static subjectes have been loaded.
     *
     * @return true if the static subjects of the principal have been loaded.
     */
    public boolean isSubjectsInitialized(){
        return subjects!=null?true:false;
    }

    /**
     * initialize the static subjects associated with the principal.
     * @param subjectsID an array of all the static Subjects ID,must NOT null;
     */
    public synchronized void initSubjects(String[] subjectsID){
        //check if subjects is null, if not null, then return directly;
        if(subjects == null){
            subjects = new HashSet();
            for(int i=0;i<subjectsID.length;i++){
                subjects.add(subjectsID[i]);
            }
        }
    }
}
