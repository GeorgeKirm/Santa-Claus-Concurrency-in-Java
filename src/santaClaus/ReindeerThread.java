/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santaClaus;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xaronai
 */
public class ReindeerThread implements Runnable{
    private final SantaMonitor monitor;
    private final int id;
    public ReindeerThread(SantaMonitor monitor, int id){
        this.monitor = monitor;
        this.id = id;
    }
    
    @Override
    public void run() {
        while(true){
            Random rand = new Random();
            int sleep = rand.nextInt(80000);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
            } finally{
                monitor.AddReindeers(id);
            }

//            try {
//                int sleepTimeI= (int) Math.random()*1000000000;
//                Thread.sleep(sleepTimeI);
//            } catch (InterruptedException ex) {
//            } finally{
//                monitor.AddReindeers(id);
//            }
        
        }
    }
}
