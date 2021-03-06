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

package at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.deploymentDescription;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.abstractModelXML.CloudServiceXML;
import at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.abstractModelXML.CloudServiceXML.MySchemaOutputResolver;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="DeploymentDescription", namespace="")
public class DeploymentDescription {
	@XmlElement(name = "DeploymentUnit")
	private List<DeploymentUnit> deployments = new ArrayList<DeploymentUnit>();
	@XmlAttribute(name="AccessIP")
	private String accessIP="";
	@XmlAttribute(name="CloudServiceID")
	private String cloudServiceID="";
	public List<DeploymentUnit> getDeployments() {
		return deployments;
	}
	 public  void generateXSD(String filename) throws Exception {
		 JAXBContext jaxbContext = JAXBContext.newInstance(DeploymentDescription.class);
		 SchemaOutputResolver sor = new MySchemaOutputResolver();
		 sor.createOutput("at.ac.tuwien.dsg.deploymentDescription", filename);
		 jaxbContext.generateSchema(sor);
		 sor.createOutput("at.ac.tuwien.dsg.deploymentDescription", filename);
	    }
    public class MySchemaOutputResolver extends SchemaOutputResolver {

	    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
	        File file = new File(suggestedFileName);
	        StreamResult result = new StreamResult(file);
	        result.setSystemId(file.toURI().toURL().toString());
	        return result;
	    }

	}
	public void setDeployments(List<DeploymentUnit> deployments) {
		this.deployments = deployments;
	}

	public String getCloudServiceID() {
		return cloudServiceID;
	}

	public void setCloudServiceID(String cloudServiceID) {
		this.cloudServiceID = cloudServiceID;
	}

	public String getAccessIP() {
		return accessIP;
	}

	public void setAccessIP(String accessIP) {
		this.accessIP = accessIP;
	}
}
