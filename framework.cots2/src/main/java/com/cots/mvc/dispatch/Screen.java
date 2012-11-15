/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-20
 * Time: 16:00:09
 * Version: 1.0
 */
public class Screen {

    String name;

    ArrayList preViews = new ArrayList(5);
    ArrayList postViews = new ArrayList(5);


    public Screen(String name){
        this.name = name;
    }

    /**
     * get the displayName of the screen.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get all the previews.
     *
     * @return
     */
    public ArrayList getPreViews() {
        return preViews;
    }

    /**
     * add a pre views.
     *
     * @param view
     */
    public void addPreView(ScreenView view){
        preViews.add(view);
    }

    /**
     * get all the post views.
     *
     * @return
     */
    public ArrayList getPostViews() {
        return preViews;
    }

    /**
     * add a post action view to this screen.
     *
     * @param view
     */
    public void addPostView(ScreenView view){
        postViews.add(view);
    }

    /**
     * show all the pre(action's view) views;
     *
     * @param request current HttpServletRequest object;
     * @param response current HttpServletResponse object;
     * @throws IOException
     * @throws ServletException
     */
    public void showPreViews(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        int count = preViews.size();
        for(int i=0;i<count;i++){
            ScreenView view = (ScreenView)preViews.get(i);
            RequestDispatcher rs = request.getRequestDispatcher(view.getUri());
            rs.include(request,response);
        }
    }

    /**
     * show all the post(action's view) views.
     *
     * @param request current HttpServletRequest object;
     * @param response current HttpServletResponse object;
     * @throws IOException
     * @throws ServletException
     */
    public void showPostViews(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        int count = postViews.size();
System.out.println(count);
        for(int i=0;i<count;i++){
            ScreenView view = (ScreenView)postViews.get(i);
            RequestDispatcher rs = request.getRequestDispatcher(view.getUri());
            rs.include(request,response);
        }
    }
}
