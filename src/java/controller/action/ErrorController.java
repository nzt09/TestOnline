/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.action;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class ErrorController implements java.io.Serializable{
    private String errorMessage;//错误内容
	
	public String returnMain(){
		return "/interfaces/login/login";
	}
	
	public String setNameFail(){
		return "";
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
}
