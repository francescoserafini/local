import urllib, urllib2, sys, httplib

url = "/rSYBL/restWS"
#HOST_IP="83.212.112.35"
#HOST_IP="128.130.172.214:8080"
#HOST_IP="83.212.112.148"
#HOST_IP="128.130.172.214:8081"
#HOST_IP="109.231.122.193:8081"
HOST_IP="localhost:8080"
CLOUD_APPLICATION_ID="HelloElasticity"

if __name__=='__main__':
	print "stoping control"   
	connection =  httplib.HTTPConnection(HOST_IP)
	headers={'Content-Type':'application/xml; charset=utf-8','Accept':'application/xml, multipart/related'}
	connection.request('PUT', url+'/'+CLOUD_APPLICATION_ID+'/stopControl', body=CLOUD_APPLICATION_ID,headers=headers,)
	result = connection.getresponse()
	print result.read()
	print "removing control"    
	headers={'Content-Type':'application/xml; charset=utf-8','Accept':'application/xml, multipart/related'}
	connection.request('DELETE', url+'/managedService/'+CLOUD_APPLICATION_ID, body=CLOUD_APPLICATION_ID,headers=headers,)
	result = connection.getresponse()
	print result.read()
      
 
  

 

