/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
 * E184. * This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 * Author : Georgiana Copil - e.copil@dsg.tuwien.ac.at
 */
package at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.ac.tuwien.dsg.csdg.Node;
import at.ac.tuwien.dsg.csdg.elasticityInformation.ElasticityCapabilityInformation;
import at.ac.tuwien.dsg.csdg.elasticityInformation.ElasticityRequirement;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.Strategy;
import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.CustomEvent;
import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.EventNotification;
import at.ac.tuwien.dsg.csdg.outputProcessing.eventsNotification.IEvent;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.enforcementPlugins.OfferedEnforcementCapabilities;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.enforcementPlugins.interfaces.EnforcementInterface;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.utils.Configuration;
import at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.utils.RuntimeLogger;
import at.ac.tuwien.dsg.rSybl.dataProcessingUnit.api.MonitoringAPIInterface;
import at.ac.tuwien.dsg.rSybl.dataProcessingUnit.monitoringPlugins.melaPlugin.MELA_API;
/*Inserito da Francesco*/
import it_at.unibo_tuwien.tucson_rSybl.*;
import alice.tucson.api.ITucsonOperation;
import alice.tucson.api.exceptions.TucsonInvalidAgentIdException;
import alice.tucson.utilities.Utils;
import alice.tuplecentre.api.exceptions.InvalidOperationException;
/*Inserito da Francesco*/

public class EnforcementAPI {

    private static HashMap<Node, ArrayList<Float>> avgRunningTimes = new HashMap<Node, ArrayList<Float>>();
    private boolean executingControlAction = false;
    private MonitoringAPIInterface monitoringAPIInterface;
    private Node controlledService;
    private EnforcementInterface offeredCapabilities;
    private String className;
    private int numberOfWaits = 0;
    
    /*
     * s.mariani@unibo.it CODE begins
     */
    private RespectEnforcementAPI respect;

    public EnforcementAPI() {
    	 /*
         * parameters could be easy configurable from rSYBL side, e.g. from file
         * or directives (within actionName!)
         */
        this.respect = new RespectEnforcementAPI(
                RespectEnforcementAPI.TUCSON_PORT, RespectEnforcementAPI.AID);
    }

    /*
     * s.mariani@unibo.it CODE ends
     */

    public boolean containsElasticityCapability(Node controlledService, String capability) {
        return offeredCapabilities.containsElasticityCapability(controlledService, capability);
    }

    public void setControlledService(Node controlledService, String className) {
        this.className = className;
        this.controlledService = controlledService;
        offeredCapabilities = OfferedEnforcementCapabilities.getInstance(
                className, this.controlledService);
    }

    public void setControlledService(Node controlledService) {
        this.controlledService = controlledService;
        offeredCapabilities = OfferedEnforcementCapabilities
                .getInstance(this.controlledService);
    }

    public void refreshControlService(Node cloudService) {
        controlledService = cloudService;
    }

    public boolean isExecutingControlAction() {
        return executingControlAction;
    }

    public boolean enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree) {
    	System.out.println("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)");
   	 RuntimeLogger.logger.info("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)....");
        Method foundMethod = null;
        boolean res = false;
        try {
            for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .contains(actionName.toLowerCase())) {
                    foundMethod = method;

                }
            }

            if (foundMethod != null) {
            	System.out.println("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)");
              	 RuntimeLogger.logger.info("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)....");
                Class[] partypes = new Class[parameters.length + 2];
                Object[] myParameters = new Object[parameters.length + 2];
                partypes[0] = Double.class;
                myParameters[0] = violationDegree;

                partypes[1] = Node.class;
                myParameters[1] = node;
                int i = 2;
                for (Object o : parameters) {
                    partypes[i] = o.getClass();
                    myParameters[i] = o;
                    i += 1;

                }

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    res = false;
                }

            } else {
                String myAction="enforceAction";
            	System.out.println("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)");
              	 RuntimeLogger.logger.info("enforceAction(String target, String actionName, Node node, Object[] parameters,double violationDegree)....");
                for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .equalsIgnoreCase(myAction.toLowerCase())) {
                    foundMethod = method;

                }
                }
                Class[] partypes = new Class[2];
                Object[] myParameters = new Object[2];
                partypes[1] = String.class;
                myParameters[1] = actionName;

                partypes[0] = Node.class;
                myParameters[0] = node;
                int i = 2;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(node);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits=0;
                while (!monitoringAPIInterface.isHealthy()) {
                    RuntimeLogger.logger.info("Waiting for action....");
                     numberOfWaits++;
                if (numberOfWaits>10){
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(node.getId());
                    customEvent.setMessage(node.getId()+" not healthy for "+(numberOfWaits*15000)+" seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            }
           List<String> metrics = monitoringAPIInterface
                    .getAvailableMetrics(node);
            // monitoringAPIInterface.enforcingActionStarted("ScaleIn", arg0);
            this.numberOfWaits = 0;
            while (!monitoringAPIInterface.isHealthy()) {
                boolean myMetrics = true;
                numberOfWaits++;
                if (numberOfWaits > 10) {
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(node.getId());
                    customEvent.setMessage(node.getId() + "not healthy for " + (numberOfWaits * 15000) + " seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                RuntimeLogger.logger.info("Waiting for action....");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        } catch (SecurityException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            res = false;
        }
        return res;
    }
    

    public boolean enforceAction(String actionName, Node node, Object[] parameters) {
    	System.out.println("enforceAction(String actionName, Node node, Object[] parameters)");
    	 RuntimeLogger.logger.info("Sono enforceAction(String actionName, Node node, Object[] parameters)....");
        Method foundMethod = null;
        boolean res = false;
        try {
            for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                     .contains(actionName.toLowerCase())) {
                    foundMethod = method;

                }
            }

            if (foundMethod != null) {
                Class[] partypes = new Class[parameters.length + 1];
                Object[] myParameters = new Object[parameters.length + 1];
                partypes[0] = Node.class;
                myParameters[0] = node;
                int i = 1;
                for (Object o : parameters) {
                    partypes[i] = o.getClass();
                    myParameters[i] = o;
                    i += 1;

                }

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    res = false;
                }

            } else {
                String myAction="enforceAction";
                
                for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .equalsIgnoreCase(myAction.toLowerCase())) {
                    foundMethod = method;

                }
                }
                Class[] partypes = new Class[2];
                Object[] myParameters = new Object[2];
                partypes[1] = String.class;
                myParameters[1] = actionName;

                partypes[0] = Node.class;
                myParameters[0] = node;
                int i = 2;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(node);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits=0;
                while (!monitoringAPIInterface.isHealthy()) {
                    RuntimeLogger.logger.info("Waiting for action....");
                     numberOfWaits++;
                if (numberOfWaits>10){
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(node.getId());
                    customEvent.setMessage(node.getId()+" not healthy for "+(numberOfWaits*15000)+" seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            
            }
            List<String> metrics = monitoringAPIInterface
                    .getAvailableMetrics(node);
            // monitoringAPIInterface.enforcingActionStarted("ScaleIn", arg0);
            numberOfWaits = 0;
            while (!monitoringAPIInterface.isHealthy()) {
                RuntimeLogger.logger.info("Waiting for action....");
                numberOfWaits++;
                if (numberOfWaits > 10) {
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(node.getId());
                    customEvent.setMessage(node.getId() + "not healthy for " + (numberOfWaits * 15000) + " seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }


            }


        } catch (SecurityException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            res = false;
        }

        return res;
    }

    public boolean scalein(Node arg0) {
    	System.out.println("Sono scalein Node Arg");
     	RuntimeLogger.logger.info("Sono scalein Node Arg0");
        boolean res = false;
        if (arg0.getAllRelatedNodes().size() > 1) {

            res = offeredCapabilities.scaleIn(arg0);
            List<String> metrics = monitoringAPIInterface
                    .getAvailableMetrics(arg0);
            numberOfWaits = 0;
            while (!monitoringAPIInterface.isHealthy()) {
                numberOfWaits++;
                if (numberOfWaits > 10) {
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(arg0.getId());
                    customEvent.setMessage(arg0.getId() + "not healthy for " + (numberOfWaits * 15000) + " seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                RuntimeLogger.logger.info("Waiting for action....");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }

            // monitoringAPIInterface.enforcingActionEnded("ScaleIn", arg0);
            RuntimeLogger.logger.info("Finished scaling in " + arg0.getId()
                    + " ...");

        } else {
            res = false;
            RuntimeLogger.logger.info("Number of nodes associated with "
                    + arg0.getAllRelatedNodes().size());
        }
        return res;
    }

    public boolean scaleout(Node arg0) {
        boolean res = true;
        res = offeredCapabilities.scaleOut(arg0);
        List<String> metrics = monitoringAPIInterface.getAvailableMetrics(arg0);
        // monitoringAPIInterface.enforcingActionStarted("ScaleOut", arg0);
        numberOfWaits = 0;
        while (!monitoringAPIInterface.isHealthy()) {
            numberOfWaits++;
            if (numberOfWaits > 10) {
                EventNotification eventNotification = EventNotification.getEventNotification();
                CustomEvent customEvent = new CustomEvent();
                customEvent.setCloudServiceID(this.getControlledService().getId());
                customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                customEvent.setTarget(arg0.getId());
                customEvent.setMessage(arg0.getId() + "not healthy for " + (numberOfWaits * 15000) + " seconds.");
                eventNotification.sendEvent(customEvent);
            }
            RuntimeLogger.logger.info("Waiting for action....");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }


        }
        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();

        }
        // monitoringAPIInterface.enforcingActionEnded("ScaleOut", arg0);
        RuntimeLogger.logger.info("Finished scaling out " + arg0.getId()
                + " ...");
        return res;
    }

    public boolean scaleout(Node arg0,double violationDegree) {
        boolean res = true;
        res = offeredCapabilities.scaleOut(arg0,violationDegree);
        List<String> metrics = monitoringAPIInterface.getAvailableMetrics(arg0);
        // monitoringAPIInterface.enforcingActionStarted("ScaleOut", arg0);
        numberOfWaits = 0;
        while (!monitoringAPIInterface.isHealthy()) {
            numberOfWaits++;
            if (numberOfWaits > 10) {
                EventNotification eventNotification = EventNotification.getEventNotification();
                CustomEvent customEvent = new CustomEvent();
                customEvent.setCloudServiceID(this.getControlledService().getId());
                customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                customEvent.setTarget(arg0.getId());
                customEvent.setMessage(arg0.getId() + " not healthy for " + (numberOfWaits * 15000) + " seconds.");
                eventNotification.sendEvent(customEvent);
            }
            RuntimeLogger.logger.info("Waiting for action....");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();

        }
        // monitoringAPIInterface.enforcingActionEnded("ScaleOut", arg0);
        RuntimeLogger.logger.info("Finished scaling out " + arg0.getId()
                + " ...");
        return res;
    }

    /*
     * s.mariani@unibo.it CODE ends
     */
    public void enforceAction( String actionName,  Node e) {
    	System.out.println("Sono enforceAction( String actionName,  Node e");
   	 RuntimeLogger.logger.info("enforceAction( String actionName,  Node e)....");
        RuntimeLogger.logger
                .info("~~~~~~~~~~~Trying to execute action executingControlaction="
                        + this.executingControlAction);
        if (this.executingControlAction == false) {
            /*
             * s.mariani@unibo.it CODE begins
             */
            if (e != null) {
                RuntimeLogger.logger.info("Enforcing action " + actionName
                        + " on the node " + e + " ...");
            } else {
                RuntimeLogger.logger.info("Enforcing coordination action "
                        + actionName + " ...");
            }
            this.executingControlAction = true;
            String args;
            if (actionName.contains("scaleout")) {
                if ("scaleout".length() == actionName.length()) {
                    args = null;
                } else {
                    args = actionName.substring("scaleout(".length(),
                            actionName.length() - 1);
                }
                this.doCoordinatedScaleOut(args, e);
            } else if (actionName.startsWith("scalein")) {
                if ("scalein".length() == actionName.length()) {
                    args = null;
                } else {
                    args = actionName.substring("scalein(".length(),
                            actionName.length() - 1);
                }
                this.doCoordinatedScaleIn(args, e);
            } else if (actionName.startsWith("monitorMetrics")) {
                if ("monitorMetrics".length() == actionName.length()) {
                    args = null;
                } else {
                    args = actionName.substring("monitorMetrics(".length(),
                            actionName.length() - 1);
                }
                this.doCoordinatedMonitorMetrics(args, e);
            } else {
                this.respect.delegate(actionName,
                        RespectEnforcementAPI.OP_TIMEOUT, e);
            }
            this.executingControlAction = false;
            if (e != null) {
                RuntimeLogger.logger.info("Finished enforcing action "
                        + actionName + " on the node " + e + " ...");
            } else {
                RuntimeLogger.logger
                        .info("Finished enforcing coordination action "
                                + actionName + " ...");
            }
            /*
             * s.mariani@unibo.it CODE ends
             */
        }
    }

    /*
     * s.mariani@unibo.it CODE ends
     */
    /*
    public boolean enforceAction(String actionName, Node e) {

        Method foundMethod = null;
        boolean res = false;
        try {
            for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .contains(actionName.toLowerCase()) || actionName.toLowerCase().contains(method.getName().toLowerCase())) {
                    foundMethod = method;

                }
            }

            if (foundMethod != null) {
                Class[] partypes = new Class[1];
                Object[] myParameters = new Object[1];
                partypes[0] = Node.class;
                myParameters[0] = e;
                int i = 1;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits = 0;
                while (!monitoringAPIInterface.isHealthy()) {
                    numberOfWaits++;
                    if (numberOfWaits > 10) {
                        EventNotification eventNotification = EventNotification.getEventNotification();
                        CustomEvent customEvent = new CustomEvent();
                        customEvent.setCloudServiceID(this.getControlledService().getId());
                        customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                        customEvent.setTarget(e.getId());
                        customEvent.setMessage(e.getId() + " not healthy for " + (numberOfWaits * 15000) + " seconds.");
                        eventNotification.sendEvent(customEvent);
                    }
                    RuntimeLogger.logger.info("Waiting for action....");
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            } else {
                 
                String myAction="enforceAction";
                
                for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .equalsIgnoreCase(myAction.toLowerCase())) {
                    foundMethod = method;

                }
                }
                Class[] partypes = new Class[2];
                Object[] myParameters = new Object[2];
                partypes[1] = String.class;
                myParameters[1] = actionName;

                partypes[0] = Node.class;
                myParameters[0] = e;
                int i = 2;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits=0;
                while (!monitoringAPIInterface.isHealthy()) {
                    RuntimeLogger.logger.info("Waiting for action....");
                     numberOfWaits++;
                if (numberOfWaits>10){
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(e.getId());
                    customEvent.setMessage(e.getId()+" not healthy for "+(numberOfWaits*15000)+" seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            }

            
        } catch (SecurityException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            res = false;
        }

        return res;

    }
*/
    public boolean enforceAction( String actionName, Node e,double violationDegree) {
    	System.out.println("Sono enforceAction( String actionName, Node e,double violationDegree)");
      	 RuntimeLogger.logger.info("enforceAction( String actionName, Node e,double violationDegree)....");
        if (className == null) {
            className = Configuration.getEnforcementPlugin();
        }
        Method foundMethod = null;
        boolean res = false;
        try {
            for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .equalsIgnoreCase(actionName.toLowerCase())) {
                    foundMethod = method;

                }
            }
            
            if (foundMethod != null) {
                Class[] partypes = new Class[2];
                Object[] myParameters = new Object[2];
                partypes[0] = Double.class;
                myParameters[0] = violationDegree;

                partypes[1] = Node.class;
                myParameters[1] = e;
                int i = 2;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits=0;
                while (!monitoringAPIInterface.isHealthy()) {
                    RuntimeLogger.logger.info("Waiting for action....");
                     numberOfWaits++;
                if (numberOfWaits>10){
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(e.getId());
                    customEvent.setMessage(e.getId()+" not healthy for "+(numberOfWaits*15000)+" seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            } else {
                String myAction="enforceAction";
                
                for (Method method : Class.forName(className).getMethods()) {
                if (method.getName().toLowerCase()
                        .equalsIgnoreCase(myAction.toLowerCase())) {
                    foundMethod = method;

                }
                }
                Class[] partypes = new Class[2];
                Object[] myParameters = new Object[2];
                partypes[1] = String.class;
                myParameters[1] = actionName;

                partypes[0] = Node.class;
                myParameters[0] = e;
                int i = 2;

                Method actionMethod;

                try {
                    actionMethod = Class.forName(className).getMethod(
                            foundMethod.getName(), partypes);

                    res = (boolean) actionMethod.invoke(offeredCapabilities,
                            myParameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    res = false;
                }
                List<String> metrics = monitoringAPIInterface
                        .getAvailableMetrics(e);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
                numberOfWaits=0;
                while (!monitoringAPIInterface.isHealthy()) {
                    RuntimeLogger.logger.info("Waiting for action....");
                     numberOfWaits++;
                if (numberOfWaits>10){
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(e.getId());
                    customEvent.setMessage(e.getId()+" not healthy for "+(numberOfWaits*15000)+" seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }


                }
            }
            
        } catch (SecurityException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            res = false;
        }

        return res;

    }

    public void setMonitoringPlugin(MonitoringAPIInterface monitoringInterface) {
        monitoringAPIInterface = monitoringInterface;
        offeredCapabilities.setMonitoringPlugin(monitoringInterface);
    }

    public void undeployService(Node service) {
        offeredCapabilities.undeployService(service);
    }

    public Node getControlledService() {
        return offeredCapabilities.getControlledService();
    }
     public void dynamicallyScale(Node service, Strategy strategy) {
         offeredCapabilities.diagonallyScale(service, strategy);
    }

    public void submitElasticityRequirements(
            ArrayList<ElasticityRequirement> description) {
        // TODO Auto-generated method stub
    }

    // TODO depending on the protocol specified and the parameters, call the
    // capability = default parameter - Service Part ID
    public boolean enforceElasticityCapability(ElasticityCapabilityInformation capability,
            Node e) {
        boolean res = false;
        if (e != null) {
            RuntimeLogger.logger.info("Enforcing capability " + capability.getApiMethod()
                    + " ...");
            if (capability.getCallType().toLowerCase().contains("rest")) {
                URL url = null;
                HttpURLConnection connection = null;
                try {

                    url = new URL(capability.getEndpoint());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    if (capability.getCallType().toLowerCase().contains("post")) {
                        connection.setRequestMethod("POST");
                    } else {
                        connection.setRequestMethod("PUT");
                    }

                    // write message body
                    OutputStream os = connection.getOutputStream();

                    if (capability.getParameter().size() == 0) {
                        connection.setRequestProperty("Content-Type",
                                "text/plain");
                        connection.setRequestProperty("Accept", "text/plain");
                        os.write(e.getId().getBytes());
                    } else {
                        // tODO: add parameters here parameter=x
                    }
                    os.flush();
                    os.close();
                    res = true;
                    InputStream errorStream = connection.getErrorStream();
                    if (errorStream != null) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(errorStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            Logger.getLogger(MELA_API.class.getName()).log(
                                    Level.SEVERE, line);
                            res = false;
                        }
                    }

                    InputStream inputStream = connection.getInputStream();
                    if (inputStream != null) {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            Logger.getLogger(MELA_API.class.getName()).log(
                                    Level.SEVERE, line);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MELA_API.class.getName()).log(
                            Level.SEVERE, ex.getMessage(), e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } else {
                if (capability.getCallType().toLowerCase().contains("plugin")) {
//                    res = offeredCapabilities.enforceAction(
//                            capability.getEndpoint(), e);
                }
            }
            RuntimeLogger.logger.info("Finished enforcing action "
                    + capability.getName() + " on the node " + e + " ...");
        }
        return res;
    }

    public void setExecutingControlAction(boolean executingControlAction) {
        this.executingControlAction = executingControlAction;
    }
       public boolean scalein(Node arg0, double violationDegree) {
    	   System.out.println("Sono scalein Node Arg double violationdegree");
       	 RuntimeLogger.logger.info("eono scalein Node Arg double violationdegree....");
            boolean res = false;
        if (arg0.getAllRelatedNodes().size() > 1) {

            res = offeredCapabilities.scaleIn(arg0,violationDegree);
            List<String> metrics = monitoringAPIInterface
                    .getAvailableMetrics(arg0);
            numberOfWaits = 0;
            while (!monitoringAPIInterface.isHealthy()) {
                numberOfWaits++;
                if (numberOfWaits > 10) {
                    EventNotification eventNotification = EventNotification.getEventNotification();
                    CustomEvent customEvent = new CustomEvent();
                    customEvent.setCloudServiceID(this.getControlledService().getId());
                    customEvent.setType(IEvent.Type.UNHEALTHY_SP);
                    customEvent.setTarget(arg0.getId());
                    customEvent.setMessage(arg0.getId() + "not healthy for " + (numberOfWaits * 15000) + " seconds.");
                    eventNotification.sendEvent(customEvent);
                }
                RuntimeLogger.logger.info("Waiting for action....");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
            // monitoringAPIInterface.enforcingActionEnded("ScaleIn", arg0);
            RuntimeLogger.logger.info("Finished scaling in " + arg0.getId()
                    + " ...");

        } else {
            res = false;
            RuntimeLogger.logger.info("Number of nodes associated with "
                    + arg0.getAllRelatedNodes().size());
        }
        return res;
       }
    public boolean enforceAction(String actionName, String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean enforceAction(String actionName, Node e, String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean enforceAction(String actionName, Node e, String parameter1, String parameter2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /*
     * s.mariani@unibo.it CODE begins
     */
    /**
     * @param actionName
     * @param e
     *            the node to be monitored
     */
    private void doCoordinatedMonitorMetrics(String actionName, Node node) {
        try {
            // this.respect.setMonitorMetricsPath(actionName);
            if (actionName != null) {
                this.respect.delegate("set_s(" + Utils.fileToString(actionName)
                        + ")", Long.MAX_VALUE, node);
            }
            // this.respect
            // .setMonitorMetricsPath(RespectEnforcementAPI.MONITOR_METRICS_PATH);
            // this.respect
            // .delegate(
            // "out_s("
            // + Utils.fileToString(RespectEnforcementAPI.MONITOR_METRICS_PATH)
            // + ")", Long.MAX_VALUE, node);
            new SyblMonitoringAgent(SyblMonitoringAgent.AID,
                    RespectEnforcementAPI.TUCSON_PORT, node).go();
            this.respect.delegate("out(sampleMetrics('" + node.getId() + "'))",
                    Long.MAX_VALUE, node);
        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TucsonInvalidAgentIdException e) {
            // cannot happen
        }
    }

    /**
     * Made by Francesco Serafini
     * @param actionName
     * @param node
     *            the node to be scaled in
     */
    private void doCoordinatedScaleIn(String actionName,  Node node) {
    	System.out.println("Sono do coordinated Scale In");
    	RuntimeLogger.logger.info("~~~~~~~~~~~Trying to execute action executingControlaction="+ this.executingControlAction);
		if ( node != null) {
		    RuntimeLogger.logger.info("Scaling in " + node + " ...");
		    this.executingControlAction = true;
		    // call CloudAPI for scaling
		    // this.offeredCapabilities.scalein(node);
		    // delegate scaling in to ReSpecT!
		    try {
		        if (actionName != null) {
		            this.respect.delegate(
		                    "set_s(" + Utils.fileToString(RespectEnforcementAPI.MONITOR_METRICS_PATH) + ")",
		                    Long.MAX_VALUE, node);
		        }
		        new SyblScaleInAgent(SyblScaleInAgent.AID, RespectEnforcementAPI.TUCSON_PORT, this.controlledService, node).go();
		        this.respect.delegate("out(doScaleIn('" + node.getId() + "'))", Long.MAX_VALUE, node);
		    } catch (TucsonInvalidAgentIdException e) {
		        // cannot happen
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    RuntimeLogger.logger.info("Waiting for action....");
		    // delegate monitoring to ReSpecT!
		    this.doCoordinatedMonitorMetrics(actionName, node);
		    // ((MonitoringAPI) this.monitoringAPIInterface)
		    // .delegateMetricsReadingToRespect(node);
		    // coordinate with ReSpecT tc to get metrics!
		    ITucsonOperation op = null;
		     List<ITucsonOperation> ops = new LinkedList<ITucsonOperation>();
		    for (int i = 0; i < RespectEnforcementAPI.MAX_TRIES; i++) {
		        op = this.respect.delegate("in(metric(node('" + node.getId()
		                + "'), name(M), value(V)))",
		                RespectEnforcementAPI.OP_TIMEOUT, node);
		        if (op.isResultSuccess()) {
		            ops.add(op);
		        }
		    }
		    // do something with metrics (e.g. log)...
		    for ( ITucsonOperation o : ops) {
		        try {
		            RuntimeLogger.logger.info("Metric "
		                    + o.getLogicTupleResult().getArg(1).getArg(0)
		                    + " has value "
		                    + o.getLogicTupleResult().getArg(2).getArg(0));
		        } catch ( InvalidOperationException e) {
		            // cannot happen
		            e.printStackTrace();
		        }
		    }
		    // coordinate with ReSpecT tc for a successful scaling!
		    op = this.respect.delegate("in(scaleIn('" + node.getId()
		            + "'), done(B))", RespectEnforcementAPI.OP_TIMEOUT, node);
		    this.executingControlAction = false;
		    if (op.isResultSuccess()) {
		        try {
		            RuntimeLogger.logger.info("Finished scaling out "
		                    + node.getId() + " : "
		                    + op.getLogicTupleResult().getArg(1).getArg(0));
		        } catch (InvalidOperationException e) {
		            // cannot happen
		        }
		    } else {
		        RuntimeLogger.logger.info("Finished scaling out "
		                + node.getId() + " failed!");
		    }
		} else {
		    RuntimeLogger.logger.info(node);
		}
		}

    /**
     * @param actionName
     * @param node
     *            the node to be scaled out
     * 
     */
    private void doCoordinatedScaleOut( String actionName,  Node node) {
        RuntimeLogger.logger
                .info("~~~~~~~~~~~Trying to execute action executingControlaction="
                        + this.executingControlAction);
        if ( node != null) {
            RuntimeLogger.logger.info("Scaling out " + node + " ...");
            this.executingControlAction = true;
            // call CloudAPI for scaling
            // this.offeredCapabilities.scaleOut(node);
            // delegate scaling out to ReSpecT!
            try {
                if (actionName != null) {
                    this.respect.delegate(
                            "set_s(" + Utils.fileToString(RespectEnforcementAPI.MONITOR_METRICS_PATH) + ")",
                            Long.MAX_VALUE, node);
                }
                new SyblScaleOutAgent(SyblScaleOutAgent.AID,
                        RespectEnforcementAPI.TUCSON_PORT,
                        this.controlledService, node).go();
                this.respect.delegate(
                        "out(doScaleOut('" + node.getId() + "'))",
                        Long.MAX_VALUE, node);
            } catch (TucsonInvalidAgentIdException e) {
                // cannot happen
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            RuntimeLogger.logger.info("Waiting for action....");
            // delegate monitoring to ReSpecT!
            this.doCoordinatedMonitorMetrics(actionName, node);
            // ((MonitoringAPI) this.monitoringAPIInterface)
            // .delegateMetricsReadingToRespect(node);
            // coordinate with ReSpecT tc to get metrics!
            ITucsonOperation op = null;
             List<ITucsonOperation> ops = new LinkedList<ITucsonOperation>();
            for (int i = 0; i < RespectEnforcementAPI.MAX_TRIES; i++) {
                op = this.respect.delegate("in(metric(node('" + node.getId()
                        + "'), name(M), value(V)))",
                        RespectEnforcementAPI.OP_TIMEOUT, node);
                if (op.isResultSuccess()) {
                    ops.add(op);
                }
            }
            // do something with metrics (e.g. log)...
            for ( ITucsonOperation o : ops) {
                try {
                    RuntimeLogger.logger.info("Metric "
                            + o.getLogicTupleResult().getArg(1).getArg(0)
                            + " has value "
                            + o.getLogicTupleResult().getArg(2).getArg(0));
                } catch ( InvalidOperationException e) {
                    // cannot happen
                    e.printStackTrace();
                }
            }
            // coordinate with ReSpecT tc for a successful scaling!
            op = this.respect.delegate("in(scaleOut('" + node.getId()
                    + "'), done(B))", RespectEnforcementAPI.OP_TIMEOUT, node);
            this.executingControlAction = false;
            if (op.isResultSuccess()) {
                try {
                    RuntimeLogger.logger.info("Finished scaling out "
                            + node.getId() + " : "
                            + op.getLogicTupleResult().getArg(1).getArg(0));
                } catch (InvalidOperationException e) {
                    // cannot happen
                }
            } else {
                RuntimeLogger.logger.info("Finished scaling out "
                        + node.getId() + " failed!");
            }
        } else {
            RuntimeLogger.logger.info(node);
        }
    }
}
