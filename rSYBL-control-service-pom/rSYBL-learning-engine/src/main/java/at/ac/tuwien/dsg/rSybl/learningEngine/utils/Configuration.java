/** 
   Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184.                 This work was partially supported by the European Commission in terms of the CELAR FP7 project (FP7-ICT-2011-8 #317790).

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
package at.ac.tuwien.dsg.rSybl.learningEngine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Configuration {

    private static  Properties configuration ;

    static{
        configuration = new Properties();
        try {
            configuration.load(new FileReader( new File("./config.properties")));

			
        } catch (Exception ex) {
        	InputStream is = Configuration.class.getClassLoader().getResourceAsStream("/config.properties");
			try {
				configuration.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
    }
    public static String getCuttoff()
    {
    	return configuration.getProperty("Cutoff");
    }
    public static String getIntervalSize()
    {
    	return configuration.getProperty("IntervalSize");
    }
   
}