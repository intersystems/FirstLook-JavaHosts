// Use or operation of this code is subject to acceptance of the license
// available in the code repository for this code.
//
// Defines a business operation to be used as part of the InterSystems IRIS 2018.1.1 
// First Look: Connecting Systems in InterSystems IRIS Using Java Business Hosts

package JavaHosts;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.intersystems.gateway.bh.BusinessOperation;


public class JavaHostsOperation implements BusinessOperation {

  public static final String SETTINGS = "LogFile";
  private PrintWriter logFile = new PrintWriter(System.out, true);
  
  @Override
  public boolean OnInit(String[] args) throws Exception {
    for (int i = 0; i < args.length-1; i++) {
      if (args[i] != null && args[i].equals("-LogFile")) {
        logFile = new PrintWriter(new FileOutputStream(args[++i], true), true);
      }
    }

    logFile.print("Starting up with arguments: ");
    for (String arg : args) {
      logFile.print(arg+" ");
    }
    logFile.println();
    return true;
  }

  @Override
  public boolean OnTearDown() throws Exception {
    return true;
  }

  @Override
  public boolean OnMessage(String message) throws Exception {
    logFile.println("Received message: "+message);
    return true;
  }

}