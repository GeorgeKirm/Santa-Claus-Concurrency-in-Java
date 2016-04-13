/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santaClaus;

import java.util.Random;

/**
 *
 * @author xaronai
 */
public class ElveThread implements Runnable {
    private final SantaMonitor monitor;
    private final int id;
    public ElveThread(SantaMonitor monitor, int id){
        this.monitor = monitor;
        this.id= id;
    }
    
    @Override
    public void run() {
        while(true){
            Random rand = new Random();
            int sleep = rand.nextInt(50000);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
            } finally{
                monitor.addElves(id);
            }
//            try {
//                int sleepTimeI= (int) Math.random()*1000000000;
//                Thread.sleep(sleepTimeI);
//            } catch (InterruptedException ex) {
//            } finally{
//                monitor.addElves(id);
//            }
        }
    }
}
