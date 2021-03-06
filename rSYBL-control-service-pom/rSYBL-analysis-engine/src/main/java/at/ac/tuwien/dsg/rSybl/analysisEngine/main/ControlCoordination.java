/** 
   Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184.               
   
   This work was partially supported by the European Commission in terms of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 *  Author : Georgiana Copil - e.copil@dsg.tuwien.ac.at
 */

package at.ac.tuwien.dsg.rSybl.analysisEngine.main;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.ResourceBundle.Control;

import at.ac.tuwien.dsg.csdg.DependencyGraph;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLAnnotation;
import at.ac.tuwien.dsg.csdg.inputProcessing.checkpointing.CheckpointingUtils;
import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.InputProcessing;
import at.ac.tuwien.dsg.csdg.inputProcessing.tosca.TOSCAProcessing;

public class ControlCoordination {

    private String currentControls = "";
    private HashMap<String, ControlService> controls = new HashMap<String, ControlService>();
    private CheckpointingUtils checkpointingUtils = CheckpointingUtils.getInstance();
    public ControlCoordination() {
        checkAndReinitialize();
    }
    private void checkAndReinitialize(){
        if (checkpointingUtils.checkpointingFolderExists()){
                   HashMap<String,String> compositionRules= checkpointingUtils.getCompositionRules();
                   HashMap<String,String> effects = checkpointingUtils.getEffects();
                   HashMap<String,String> serviceDescriptions = checkpointingUtils.getServiceDescription();
                   HashMap<String,String> serviceDeployments = checkpointingUtils.getDeploymentDescription();
                   for (String serviceID:compositionRules.keySet()){
                       ControlService thisService = new ControlService(serviceID);
                      
                       thisService.setApplicationDeployment(serviceDeployments.get(serviceID));
                       thisService.setApplicationDescriptionInfo(serviceDescriptions.get(serviceID));
                       thisService.setEffects(effects.get(serviceID));
                       thisService.setMetricCompositionRules(compositionRules.get(serviceID));
                       thisService.start();
                       controls.put(serviceID, thisService);
                       
                   }
                }
    }
    public void refreshApplicationDeploymentDescription(String deploymentNew) {
    }

    public void triggerHealthFixServicePart(String servicePartID, String serviceID) {
        controls.get(serviceID).triggerHealthFix(servicePartID);
    }
    public void removeService(String cloudServiceId){
        if (!cloudServiceId.equalsIgnoreCase("") && controls.containsKey(cloudServiceId)){
           controls.get(cloudServiceId).removeFromMonitoring();
        if (controls.get(cloudServiceId)!=null){
           controls.get(cloudServiceId).stop();
        
        controls.remove(cloudServiceId);
        }
        }
    }
       
    public void undeployService(String cloudServiceId){
        if (!cloudServiceId.equalsIgnoreCase("") && controls.containsKey(cloudServiceId)){
            controls.get(cloudServiceId).undeployService();
        controls.get(cloudServiceId).stop();
        
        controls.remove(cloudServiceId);
        
        }
    }
    public void prepareControl(String cloudServiceId) {
        ControlService controlService = new ControlService(cloudServiceId);
        controls.put(cloudServiceId, controlService);
        currentControls = cloudServiceId;
    }

    public void replaceRequirements(String cloudServiceID, String newReqs) {
        controls.get(cloudServiceID).replaceElasticityRequirements(newReqs);
    }

    public void setApplicationDeploymentDescription(String deployment) {
        controls.get(currentControls).setApplicationDeployment(deployment);
    }

    public void setApplicationDeploymentDescription(String currentControls, String deployment) {
        controls.get(currentControls).setApplicationDeployment(deployment);
    }

    public void setElasticityCapabilitiesEffects(String effects) {
        //AnalysisLogger.logger.info("Setting the effects for "+currentControls);
        controls.get(currentControls).setEffects(effects);
    }

    public void setElasticityCapabilitiesEffects(String currentControls, String effects) {
        //AnalysisLogger.logger.info("Setting the effects for "+currentControls);
        controls.get(currentControls).setEffects(effects);
    }

    public void setMetricComposition(String currentControls, String composition) {
        //AnalysisLogger.logger.info("Setting the metric composition rules for "+currentControls);
        controls.get(currentControls).setMetricCompositionRules(composition);
    }

    public void setMetricComposition(String composition) {
        //AnalysisLogger.logger.info("Setting the metric composition rules for "+currentControls);
        controls.get(currentControls).setMetricCompositionRules(composition);
    }

    public void setApplicationDescriptionInfo(String descriptionInfo) {
        controls.get(currentControls).setApplicationDescriptionInfo(descriptionInfo);
    }

    public void setApplicationDescriptionInfo(String currentControls, String descriptionInfo) {
        controls.get(currentControls).setApplicationDescriptionInfo(descriptionInfo);
    }

    public void startControl(String cloudServiceId) {
        controls.get(cloudServiceId).start();
    }

    public void stopControl(String cloudServiceId) {
        controls.get(cloudServiceId).stop();
    }

    public void processAnnotation(String serviceId, String entity, SYBLAnnotation annotation) {
        try {
            controls.get(serviceId).processAnnotation(entity, annotation);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void replaceCloudServiceWithRequirements(String cloudServiceId, String cloudService) {
        controls.get(cloudServiceId).replaceCloudServiceRequirements(cloudService);
    }

    public void replaceCloudServiceWithRequirements(String cloudService) {
        controls.get(currentControls).replaceCloudServiceRequirements(cloudService);
    }

    public void replaceEffects(String id, String effects) {
        controls.get(currentControls).replaceEffects(effects);
    }

    public void replaceEffects(String effects) {
        controls.get(currentControls).replaceEffects(effects);
    }

    public void replaceCompositionRules(String compositionRules) {
        controls.get(currentControls).replaceCompositionRules(compositionRules);
    }

    public void replaceCompositionRules(String currentControls, String compositionRules) {
        controls.get(currentControls).replaceCompositionRules(compositionRules);
    }

    public void replaceRequirementsString(String cloudServiceId, String requirements) {
        requirements.replaceAll("<", "&lt;");
        requirements.replaceAll(">", "&gt;");
        controls.get(cloudServiceId).replaceRequirements(requirements);

    }
    public String getRequirements(String cloudServiceId){
        if (controls.containsKey(cloudServiceId))
        return controls.get(cloudServiceId).getXMLRequirements();
        else return "";
    }
    public String getSimpleRequirements(String cloudServiceId){
        if (controls.containsKey(cloudServiceId))
        return controls.get(cloudServiceId).getSimpleRequirements();
        else return "";
    }
    public void replaceRequirements(String requirements) {
        
        controls.get(currentControls).replaceElasticityRequirements(requirements);
    }

    public void setApplicationDescriptionInfoInternalModel(String applicationDescriptionXML, String elasticityRequirementsXML, String deploymentInfoXML) {
        InputProcessing inputProcessing = new InputProcessing();
        DependencyGraph dependencyGraph = inputProcessing.loadDependencyGraphFromStrings(applicationDescriptionXML, elasticityRequirementsXML, deploymentInfoXML);
        ControlService controlService = new ControlService(dependencyGraph.getCloudService().getId());
        controlService.setDependencyGraph(dependencyGraph);
        controls.put(dependencyGraph.getCloudService().getId(), controlService);

    }

    public void setAndStartToscaControl(String tosca) {
        TOSCAProcessing inputProcessing = new TOSCAProcessing();
        DependencyGraph dependencyGraph = inputProcessing.toscaDescriptionToDependencyGraph(tosca);
        ControlService controlService = new ControlService(dependencyGraph.getCloudService().getId());
        controlService.setDependencyGraph(dependencyGraph);
        controls.put(dependencyGraph.getCloudService().getId(), controlService);
        controls.get(dependencyGraph.getCloudService().getId()).start();
    }
 public boolean testEnforcementCapability(String serviceName, String enforcementName, String componentID){
      return controls.get(serviceName).testEnforcementCapability(enforcementName, componentID);
    }
    public boolean testEnforcementCapabilityOnPlugin(String serviceName,String target,String enforcementName, String componentID){
      return controls.get(serviceName).testEnforcementCapabilityOnPlugin(target,enforcementName, componentID);
    }
    public boolean setTESTState(String serviceName){
        if (controls.containsKey(serviceName)){
            controls.get(serviceName).setStateTEST();
            return true;
          }
        else
        {
            return false;
        }
    }
    public String getJSONStructureOfService(String id) {
        if (controls.containsKey(id)){
        return controls.get(id).getJSONStructureOfService();
        }else{
            return "";
        }
    }

    public String getApplicationDescriptionInfo(String id) {
        if (!id.equalsIgnoreCase("") && controls.size()>0)
            return controls.get(id).getApplicationDescriptionInfo();
        else return "";
    }
    public void setApplicationDescriptionInfoTOSCA(String tosca, String serviceID){
        
        controls.get(serviceID).setApplicationDescriptionInfoTOSCABased(tosca);
    }
    public void startControlOnExisting( String serviceID){
        controls.get(serviceID).startControlOnExisting();
    }
    public String getServices() {
        String services = "";
        for (String serv : controls.keySet()) {
            services += serv + ",";
        }
        return services;
    }
}
