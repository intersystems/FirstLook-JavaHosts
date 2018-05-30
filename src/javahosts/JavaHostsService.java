// Use or operation of this code is subject to acceptance of the license
// available in the code repository for this code.
//
// Defines a business service to be used as part of the InterSystems IRIS 2018.1.1 
// First Look: Connecting Systems in InterSystems IRIS Using Java Business Hosts

package JavaHosts;

import java.util.Random;
import com.intersystems.gateway.bh.BusinessService;
import com.intersystems.gateway.bh.Production;
import com.intersystems.gateway.bh.Production.Severity;

public class JavaHostsService implements BusinessService {
  
  public static final String SETTINGS = "Min,Max";
  static Thread messageThread = null;
    Production production = null;
    
  @Override
  public boolean OnInit(Production p) throws Exception {
    production = p;

    if (messageThread == null) {
      Messager messager = new Messager();
      messageThread = new Thread(messager);
      messageThread.start();
    }
  
    return true;
  }

  @Override
  public boolean OnTearDown() throws Exception {
    if (messageThread != null) {
      if (messageThread.isAlive()) {
        messageThread.interrupt();
        messageThread.join();
      }
      messageThread = null;
    }
    
    return true;
  }

  private class Messager implements Runnable {
    Random rand = new Random();
      
    public void run() {
      try {
        int min = 0, max = 100;
        
        String setting = production.GetSetting("Min");
        if (!setting.isEmpty()) {
          min = Integer.parseInt(setting);
        }
        setting = production.GetSetting("Max");
        if (!setting.isEmpty()) {
          max = Integer.parseInt(setting);
        }

        production.LogMessage("Starting up with min: "+min+" max: "+max, Severity.INFO);
      
        while (true) {
          Integer value = rand.nextInt(max - min) + min;
          production.LogMessage("Sending message: "+value, Severity.TRACE);
          production.SendRequest(value.toString());
          Thread.sleep(5000);
        }
      } catch (InterruptedException e) {
        try {
          production.LogMessage("Shutting down", Severity.INFO);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        return;
      } catch (Exception e) {
        try {
          production.LogMessage(e.toString(), Severity.ERROR);
          production.SetStatus(Production.Status.ERROR);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    }  
  }

}