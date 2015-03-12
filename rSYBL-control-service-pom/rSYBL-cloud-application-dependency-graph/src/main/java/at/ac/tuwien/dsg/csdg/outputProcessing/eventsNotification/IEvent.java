/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification;

import java.io.Serializable;

/**
 *
 * @author Georgiana
 */
public interface IEvent extends Serializable{
    public enum Stage {
		START, FINISHED
	}
	public enum Type {
		NOTIFICATION, ERROR, UNHEALTHY_SP
	}
    public Stage getStage();


	public String getServiceId() ;

        
}