/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.importent;

/**
 *
 * @author Administrator
 */
import java.io.IOException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletResponse;

public class ViewExpiredExceptionExceptionHandler extends
        ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                FacesContext fc = FacesContext.getCurrentInstance();
                // we can do what we wnat to do at here, for example in here, we just redirect to home page, then will be redirect to login page
                try {
                    HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                    response.sendRedirect("/TestOnlineFree-war/faces/interfaces/login/login.xhtml");
                    fc.renderResponse();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    i.remove();
                }
            }
        }
        getWrapped().handle();
    }
}
