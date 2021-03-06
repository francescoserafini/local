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

package at.ac.tuwien.dsg.csdg.inputProcessing.multiLevelModel.abstractModelXML;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLAnnotation;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InitializationSequence" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="call" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AssociatedIps" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="ip" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Action" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="apiMethod" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="parameter" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="associatedOpenstackSnapshot" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="associatedInitialIp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceUnit")
public  class ServiceUnitXML {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @XmlAttribute(name = "id")
	private String id;
	 @XmlElement(name = "SYBLDirective")
		private SYBLAnnotationXML syblAnnotationXML;
          @XmlElement(name = "LinearRelationship")
		private List<LinearRelationshipXML> linearRelationships;
              @XmlElement (name="Properties", required=false)
    private List<PropertyXML> properties = new ArrayList<PropertyXML>();
    /**
     * @return the linearRelationships
     */
    public List<LinearRelationshipXML> getLinearRelationships() {
        return linearRelationships;
    }

    /**
     * @param linearRelationships the linearRelationships to set
     */
    public void setLinearRelationships(List<LinearRelationshipXML> linearRelationships) {
        this.linearRelationships = linearRelationships;
    }


          
	 public SYBLAnnotationXML getXMLAnnotation(){
		 return syblAnnotationXML;
	 }
	 public void setXMLAnnotation(SYBLAnnotationXML annotation){
		 syblAnnotationXML=annotation;
	 }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    protected String associatedInitialIp;
    protected List<String> associatedIps = new ArrayList<String>();
   @XmlElement(name = "CodeRegions")
 	private List<CodeRegionXML> codeRegions = new ArrayList<CodeRegionXML>();
    
    @XmlElement(name = "ElasticityCapability")
 	private List<ElasticityCapabilityXML> actions = new ArrayList<ElasticityCapabilityXML>();
    
    @XmlElement(name = "InitializationSequence")
 	private InitializationSequenceXML initializationSequence;
    
    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CloudServiceXML.ComponentTopologyXML.ComponentXML.InitializationSequenceXML }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link CloudServiceXML.ComponentTopologyXML.ComponentXML.AssociatedIp }{@code >}
     * {@link JAXBElement }{@code <}{@link CloudServiceXML.ComponentTopologyXML.ComponentXML.ActionXML }{@code >}
     * 
     * 
     */
   

   

    /**
     * Gets the value of the associatedInitialIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedInitialIp() {
        return associatedInitialIp;
    }

    /**
     * Sets the value of the associatedInitialIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedInitialIp(String value) {
        this.associatedInitialIp = value;
    }


    public List<String> getAssociatedIps() {
		return associatedIps;
	}

	public void setAssociatedIps(List<String> associatedIps) {
		this.associatedIps = associatedIps;
	}



	public List<CodeRegionXML> getCodeRegions() {
		return codeRegions;
	}

	public void setCodeRegions(List<CodeRegionXML> codeRegions) {
		this.codeRegions = codeRegions;
	}


	

	public List<ElasticityCapabilityXML> getElasticityCapability() {
		return actions;
	}

	public void setElasticityCapability(List<ElasticityCapabilityXML> actions) {
		this.actions = actions;
	}

	public void addElasticityCapability(ElasticityCapabilityXML capabilityXML){
		actions.add(capabilityXML);
	}
	


	public InitializationSequenceXML getInitializationSequence() {
		return initializationSequence;
	}

	public void setInitializationSequence(InitializationSequenceXML initializationSequence) {
		this.initializationSequence = initializationSequence;
	}

    /**
     * @return the properties
     */
    public List<PropertyXML> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<PropertyXML> properties) {
        this.properties = properties;
    }








   
    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="call" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "call"
    })
    public static class InitializationSequenceXML {

        private List<String> call;

        /**
         * Gets the value of the call property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the call property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCall().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getCall() {
            if (call == null) {
                call = new ArrayList<String>();
            }
            return this.call;
        }

		public void setCall(List<String> call) {
			this.call = call;
		}

    }

}







