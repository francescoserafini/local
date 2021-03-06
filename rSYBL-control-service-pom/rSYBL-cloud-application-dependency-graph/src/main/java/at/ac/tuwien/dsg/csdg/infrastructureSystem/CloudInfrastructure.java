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

package at.ac.tuwien.dsg.csdg.infrastructureSystem;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.dsg.csdg.Node;




public class CloudInfrastructure extends Node{
private List<VirtualCluster> clusters = new ArrayList<VirtualCluster>();
private List<VirtualMachine> vms = new ArrayList<VirtualMachine>();
public List<VirtualCluster> getClusters() {
	return clusters;
}
public void setClusters(List<VirtualCluster> clusters) {
	this.clusters = clusters;
}
public List<VirtualMachine> getVms() {
	return vms;
}
public void setVms(List<VirtualMachine> vms) {
	this.vms = vms;
}
public void addVM(VirtualMachine vm){
	vms.add(vm);
}
public void addVirtualCluster(VirtualCluster vc){
	clusters.add(vc);
}
}
