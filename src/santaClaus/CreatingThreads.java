/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santaClaus;

/**
 *
 * @author xaronai
 */
public class CreatingThreads {
    public static void main(String[] args) {
        NorthPall GUI = new NorthPall();
        SantaMonitor monitor = new SantaMonitor(GUI);
        for (int i = 0; i < 10; i++) {
            ElveThread elvesT = new ElveThread(monitor, i);
            Thread threadOfElves = new Thread(elvesT);
            threadOfElves.start();
            if (i!=9) {
                ReindeerThread reindeerT = new ReindeerThread(monitor,i);
                Thread threadOfReindeer = new Thread(reindeerT);
                threadOfReindeer.start();   
            }   
        }   
    }
}
