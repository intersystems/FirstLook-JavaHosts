package javahosts;

import java.util.Random;
import com.intersys.gateway.bh.BusinessService;
import com.intersys.gateway.bh.Production;
import com.intersys.gateway.bh.Production.Severity;

public class javahostsService implements BusinessService {
  
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