package at.ac.tuwien.dsg.rsybl.controlStressApp.controls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
 
import com.extl.jade.user.ExtilityException;
import com.extl.jade.user.Job;
import com.extl.jade.user.JobStatus;
import com.extl.jade.user.Server;
import com.extl.jade.user.Condition;
import com.extl.jade.user.FilterCondition;
import com.extl.jade.user.ResourceType;
import com.extl.jade.user.SearchFilter;
import com.extl.jade.user.ListResult;
import com.extl.jade.user.QueryLimit;
import com.extl.jade.user.ServerStatus;
import com.extl.jade.user.UserAPI;
import com.extl.jade.user.UserService;


public class FlexiantActions extends ActionOnIaaSProvider{
	  String userEmailAddress = "cgeorgy1987@yahoo.com";
	//  String apiUserName="cgeorgy1987@yahoo.com/af809242-0ea2-3285-8bfe-708339c78fc2";
	  String apiUserName="cgeorgy1987@yahoo.com";
      String customerUUID = "af809242-0ea2-3285-8bfe-708339c78fc2";
      String password = "c3larPassword";
      String ENDPOINT_ADDRESS_PROPERTY="https://api.sd1.flexiant.net:4442";
  public void removeServer(String serverUUID){
	  UserService service;

		 URL url = ClassLoader.getSystemClassLoader().getResource(
              "UserAPI.wsdl");
		 
      // Get the UserAPI
      UserAPI api = new UserAPI(url,new QName("http://extility.flexiant.net", "UserAPI"));
      // and set the service port on the service
      service = api.getUserServicePort();
      BindingProvider portBP = (BindingProvider) service;
      
      // and set the service endpoint
      //TODO{Ask}
      System.err.println("Trying to connect to ... https://api.sd1.flexiant.net:4442 ");
      portBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
    		  ENDPOINT_ADDRESS_PROPERTY);
       
      // and the caller's authentication details and password
      portBP.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
              userEmailAddress + "/" + customerUUID);
      portBP.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
              password);
      
   
     GregorianCalendar gregorianCalendar = new GregorianCalendar();
     DatatypeFactory datatypeFactory = null;
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
     try {
		Job job=service.deleteResource(serverUUID, true, now);
		
		while (job.getStatus()!= JobStatus.FAILED && job.getStatus()!= JobStatus.SUCCESSFUL && job.getStatus()!=JobStatus.SUSPENDED){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (job.getStatus()==JobStatus.FAILED || job.getStatus()==JobStatus.SUSPENDED){
			 job=service.deleteResource(serverUUID, true, now);
			 while (job.getStatus()!= JobStatus.FAILED && job.getStatus()!= JobStatus.SUCCESSFUL && job.getStatus()!=JobStatus.SUSPENDED){
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	} catch (ExtilityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
  }
      /*
     *create new server   
     */
	public String createNewServer(String serverName,String imageUUID, int cpu, int mem){
		Server createdServer=null;
   	 UserService service;

		 URL url = ClassLoader.getSystemClassLoader().getResource(
                 "UserAPI.wsdl");
          
         // Get the UserAPI
         UserAPI api = new UserAPI(url,new QName("http://extility.flexiant.net", "UserAPI"));
                  
         // and set the service port on the service
         service = api.getUserServicePort();
          
         // Get the binding provider
         BindingProvider portBP = (BindingProvider) service;
          
         // and set the service endpoint
         portBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
        		 ENDPOINT_ADDRESS_PROPERTY);
          
         // and the caller's authentication details and password
         portBP.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                 userEmailAddress + "/" + customerUUID);
         portBP.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                 password);
      
        Server skeletonServer=new Server();
        skeletonServer.setVdcUUID("acbdb8d6-1a6e-3f90-9a1a-4bf4b0fdfc9f");
        skeletonServer.setCpu(cpu);
        skeletonServer.setInitialUser("ubuntu");
        skeletonServer.setInitialPassword("c3larPassword");
        skeletonServer.setCustomerUUID("ab8c4cae-c870-34f3-b91b-476aedd0109f");
        skeletonServer.setRam(mem);
        skeletonServer.setImageName(serverName);
        skeletonServer.setImageUUID(imageUUID);
        skeletonServer.setDeploymentInstanceUUID("9ba97cd5-28e6-342d-91db-892a4bc0914e");
        skeletonServer.setClusterUUID("1ff16f43-4a82-34bf-8f07-ea6d210548ab");
        List<String> sshs=new ArrayList<String>();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = null;
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);

        sshs.add("c2676e1f-2466-322e-a44e-69da67d4bc85");
          try {
			service.createServer(skeletonServer, sshs, now );
		} catch (ExtilityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             
		return createdServer.toString();
		
		//return createdServer.getNics().get(0).getIpAddresses().get(0).getIpAddress();
	}
     public List<Server> listServers(){
    	 UserService service;
         List<Server> servers = new ArrayList<Server>();
         // Get the service WSDL from the client jar
         URL url = ClassLoader.getSystemClassLoader().getResource(
                 "UserAPI.wsdl");
          
         // Get the UserAPI
         UserAPI api = new UserAPI(url,new QName("http://extility.flexiant.net", "UserAPI"));
                  
         // and set the service port on the service
         service = api.getUserServicePort();
          
         // Get the binding provider
         BindingProvider portBP = (BindingProvider) service;
          
         // and set the service endpoint
         portBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
        		 ENDPOINT_ADDRESS_PROPERTY);
          
         // and the caller's authentication details and password
         portBP.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                 userEmailAddress + "/" + customerUUID);
         portBP.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                 password);
      
         try {
              
             // List all servers in the running and starting states
              
             // Create an FQL filter and a filter condition
             SearchFilter sf = new SearchFilter();
             FilterCondition fc = new FilterCondition();
              
             // set the condition type
             fc.setCondition(Condition.IS_EQUAL_TO);
              
             // the field to be matched
             fc.setField("status");
              
             // and a list of values
             fc.getValue().add(ServerStatus.RUNNING.name());
             fc.getValue().add(ServerStatus.STARTING.name());
              
             // Add the filter condition to the query
             sf.getFilterConditions().add(fc);
              
             // Set a limit to the number of results
             QueryLimit lim = new QueryLimit();
             lim.setMaxRecords(10);
              
             // Call the service to execute the query
             ListResult result = service.listResources(sf, lim, ResourceType.SERVER);
              
             // Iterate through the results           
             for(Object o : result.getList()) {
                 Server s = ((Server)o);
                 System.out.println("Server " + s.getResourceUUID() + " is in state " +
                         s.getStatus());
                 servers.add(s);
             }
                              
         } catch (Exception e) {
              
             e.printStackTrace();
         }
         return servers;
     }
     
	

 
}