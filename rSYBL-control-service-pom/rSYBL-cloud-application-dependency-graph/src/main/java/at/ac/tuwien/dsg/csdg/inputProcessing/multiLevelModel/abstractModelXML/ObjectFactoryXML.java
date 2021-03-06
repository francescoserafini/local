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

// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.02.12 at 03:51:06 PM CET 
//


package at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.abstractModelXML;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;




@XmlRegistry
public class ObjectFactoryXML {
    private final static QName _CloudServiceComponentTopologyComponentAction_QNAME = new QName("", "Action");
    private final static QName _CloudServiceComponentTopologyComponentInitializationSequence_QNAME = new QName("", "InitializationSequence");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactoryXML() {
    }

    /**
     * Create an instance of {@link CloudServiceXML }
     * 
     */
    public CloudServiceXML createCloudService() {
        return new CloudServiceXML();
    }



    /**
     * Create an instance of {@link CloudServiceXML.ServiceTopologyXML }
     * 
     */
    public ServiceTopologyXML createComponentTopology() {
        return new ServiceTopologyXML();
    }

 
    /**
     * Create an instance of {@link CloudServiceXML.ServiceTopologyXML.ServiceUnitXML }
     * 
     */
    public ServiceUnitXML createComponent() {
        return new ServiceUnitXML();
    }

    /**
     * Create an instance of {@link CloudServiceXML.ServiceTopologyXML.ServiceUnitXML }
     * 
     */
    public CodeRegionXML createCodeRegion() {
        return new CodeRegionXML();
    }

   
    public ServiceUnitXML.InitializationSequenceXML createInitializationSequence(){
    	return new ServiceUnitXML.InitializationSequenceXML();
    }

    /**
     * Create an instance of {@link CloudServiceXML.ServiceTopologyXML.RelationshipXML }
     * 
     */
    public RelationshipXML createRelationship() {
        return new RelationshipXML();
    }

   



    

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloudServiceXML.ServiceTopologyXML.RelationshipXML }{@code >}}
     * 
     */
    
    public RelationshipXML createRelationship(RelationshipXML value) {
        return new RelationshipXML();
    }
   


    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloudServiceXML.ServiceTopologyXML.ServiceUnitXML.InitializationSequenceXML }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "InitializationSequenceXML", scope =ServiceUnitXML.class)
    public JAXBElement<ServiceUnitXML.InitializationSequenceXML> createComponentInitializationSequence(ServiceUnitXML.InitializationSequenceXML value) {
        return new JAXBElement<ServiceUnitXML.InitializationSequenceXML>(_CloudServiceComponentTopologyComponentInitializationSequence_QNAME, ServiceUnitXML.InitializationSequenceXML.class, ServiceUnitXML.class, value);
    }
    
   
}
