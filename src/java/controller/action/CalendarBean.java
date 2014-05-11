/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.action;

/**
 *
 * @author Administrator
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named
@SessionScoped
public class CalendarBean implements Serializable {
    private Locale locale;
    private boolean popup;
    private String pattern;
    private Date selectedDate;
    private boolean showApply = true;
    private boolean useCustomDayLabels;
    private boolean disabled = false;

    public CalendarBean() {

        locale = Locale.US;
        popup = true;
        pattern = "MM/dd/yyyy HH:mm:ss";
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void selectLocale(ValueChangeEvent event) {

        String tLocale = (String) event.getNewValue();
        if (tLocale != null) {
            String lang = tLocale.substring(0, 2);
            String country = tLocale.substring(3);
            locale = new Locale(lang, country, "");
        }
    }

    public boolean isUseCustomDayLabels() {
        return useCustomDayLabels;
    }

    public void setUseCustomDayLabels(boolean useCustomDayLabels) {
        this.useCustomDayLabels = useCustomDayLabels;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public boolean isShowApply() {
        return showApply;
    }

    public void setShowApply(boolean showApply) {
        this.showApply = showApply;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isShowApplyAvailable() {
        return !disabled && popup;
    }

    public boolean isPatternAvailable() {
        return !disabled && popup;
    }
}
