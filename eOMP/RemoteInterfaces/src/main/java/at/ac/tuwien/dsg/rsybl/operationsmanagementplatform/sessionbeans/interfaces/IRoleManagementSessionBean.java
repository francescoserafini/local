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
package at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.sessionbeans.interfaces;

import at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.interfaces.IResponsibility;
import at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.interfaces.IRole;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Georgiana
 */
@Local
public interface IRoleManagementSessionBean {

    public List<IRole> findAllRoles();

    public List<IResponsibility> findAllResponsibilities();

    public void createRole(List<String> resp, String roleName, int authority);

    public void createResponsibility(String responsibilityType, ArrayList<String> metrics, ArrayList<String> metricPatterns);

    public IResponsibility searchForResponsibilityOfType(String type);

    public void clearRoleData();

    public IRole searchForRoleWithName(String name);

}
