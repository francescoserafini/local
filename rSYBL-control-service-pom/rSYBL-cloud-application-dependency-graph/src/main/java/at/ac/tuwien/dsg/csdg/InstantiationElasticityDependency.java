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

package at.ac.tuwien.dsg.csdg;

import at.ac.tuwien.dsg.csdg.elasticityInformation.ElasticityRequirement;

public class InstantiationElasticityDependency extends SimpleRelationship{
	public enum TypesOfInstantiationDependency {SIMPLE_INSTANTIATION_DEPENDENCY, REQUIREMENT_BASED_INSTANTIATION_DEPENDENCY};
	private TypesOfInstantiationDependency instantiationDependencyType=TypesOfInstantiationDependency.SIMPLE_INSTANTIATION_DEPENDENCY;
	private String focusMetric;

	public TypesOfInstantiationDependency getInstantiationDependencyType() {
		return instantiationDependencyType;
	}

	public void setInstantiationDependencyType(
			TypesOfInstantiationDependency instantiationDependencyType) {
		this.instantiationDependencyType = instantiationDependencyType;
	}

	public String getFocusMetric() {
		return focusMetric;
	}

	public void setFocusMetric(String focusMetric) {
		this.focusMetric = focusMetric;
	}
	
	
	
}
