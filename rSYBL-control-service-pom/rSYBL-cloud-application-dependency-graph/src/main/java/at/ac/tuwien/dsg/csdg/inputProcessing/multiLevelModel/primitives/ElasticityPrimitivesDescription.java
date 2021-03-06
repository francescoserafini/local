
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


package at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.primitives;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLAnnotation;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLElasticityRequirementsDescription;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLElasticityRequirementsDescription.MySchemaOutputResolver;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ElasticityPrimitivesDescription", namespace="")
@XmlType(name="ElasticityPrimitivesDescription",propOrder="elasticityPrimitives")
public class ElasticityPrimitivesDescription implements Serializable{
	@XmlElement(name = "ServiceElasticityPrimitives")
	private List<ServiceElasticityPrimitives> elasticityPrimitives = new ArrayList<ServiceElasticityPrimitives>();

	public List<ServiceElasticityPrimitives> getElasticityPrimitives() {
		return elasticityPrimitives;
	}

	public void setElasticityPrimitives(List<ServiceElasticityPrimitives> elasticityPrimitives) {
		this.elasticityPrimitives = elasticityPrimitives;
	}
	public void addElasticityPrimitiveDescription(ServiceElasticityPrimitives elasticityPrimitivesDescription){
		elasticityPrimitives.add(elasticityPrimitivesDescription);
	}
	
}
