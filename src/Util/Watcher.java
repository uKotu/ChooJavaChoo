package Util;

import Main.Main;

import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.logging.Level;

public class Watcher extends Thread
{

    private String filePath;
    private Method updateMethod;
    private Simulation sim;

    public Watcher(String filePath, Method updateMethod, Simulation sim)
    {
        this.filePath=filePath;
        this.updateMethod=updateMethod;
        this.sim = sim;
    }

    @Override
    public void run()
    {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(filePath);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
            while(true)
            {
                WatchKey key;
                try
                {
                    key = watchService.take();
                }
                catch (InterruptedException ex) {
                    Main.logger.log(Level.WARNING, ex.getMessage(), ex);
                    return;
                }
                try
                {
                    sleep(50);
                }
                catch
                (InterruptedException ex)
                {
                    Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
                }
                for (WatchEvent<?> event : key.pollEvents())
                {
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    String fileName = ev.context().toString().trim();
                    if ((kind.equals(StandardWatchEventKinds.ENTRY_MODIFY) || kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) &&
                            (fileName.endsWith(".properties") || fileName.endsWith(".txt") || fileName.endsWith(".ser")))
                        sim.addTrain(filePath+"\\"+fileName);
                }
                boolean valid = key.reset();
                if (!valid)
                {
                    break;
                }
            }

        }
        catch (Exception  ex) {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }

}

