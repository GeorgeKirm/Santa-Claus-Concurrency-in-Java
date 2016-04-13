package santaClaus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.*;

/**
 *
 * @author xaronai
 */
public class SantaMonitor {
    private final Lock lock = new ReentrantLock(true);
    private final Lock elvesLock = new ReentrantLock(true);
    private final Lock reindeersLock = new ReentrantLock();
    
    final Condition elvesProccessBlockCondition = elvesLock.newCondition();
    final Condition reindeersProccessBlockCondition = reindeersLock.newCondition();
    final Condition elvesWaitListCondition = elvesLock.newCondition();
    
    private List<Integer> elvesGroupList = new ArrayList<>();
    private List<Integer> reindeerGroupList = new ArrayList<>();
    
    private NorthPall GUI;
    
    public SantaMonitor(NorthPall GUI){
        this.GUI= GUI;
    }
    
    /**
     * 
     */
    public void elvesCriticalSection() {
        lock.lock();
        GUI.presserElvesOnCSB();
        try {
            GUI.jTextArea1TextChanger("Elves No"+elvesGroupList.get(0)+" No"+elvesGroupList.get(1)+" and No"+elvesGroupList.get(2)+" enter CS");
            for (int i = 0; i < 3; i++) {
//                GUI.presserElveTT(elvesGroupList.get(i));
                GUI.presserElve(elvesGroupList.get(i));
            }
            for (int i = 0; i < 3; i++) {
                Random rand = new Random();
                int sleep = rand.nextInt(10000);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                }
            
//                try {
//                    int sleepTimeI= (int) Math.random()*1000000000;
//                    Thread.sleep(sleepTimeI);
//                } catch (InterruptedException ex) {
//                }
                
            }
            for (int i = 0; i < 3; i++) {
                GUI.presserElve(elvesGroupList.get(i));
            }
            GUI.jTextArea1TextChanger("Elves No"+elvesGroupList.get(0)+" No"+elvesGroupList.get(1)+" and No"+elvesGroupList.get(2)+" leave CS");
            System.out.println("~~ER");
            elvesGroupList = new ArrayList<>();
            elvesProccessBlockCondition.signalAll();
        } finally {
            GUI.presserElvesOnCSB();
            lock.unlock();
        }
    }
    
    /**
     * 
     */
    public void reindeersCriticalSection() {
        lock.lock();
        GUI.presserRaindeersOnCSB();
        try {
            GUI.jTextArea1TextChanger("Reindeers enter CS");
            for (int i = 0; i < 9; i++) {
                GUI.presserReindeer(reindeerGroupList.get(i));
            }
            for (int i = 0; i < 9; i++) {
                Random rand = new Random();
                int sleep = rand.nextInt(1000);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                }
                
//                try {
//                    long sleepTimeI= (long) Math.random()*1000000000;
//                    Thread.sleep(sleepTimeI);
//                } catch (InterruptedException ex) {
//                }
            }
            GUI.presserReindeerAll();
            GUI.jTextArea1TextChanger("Reindeers leave CS");
            System.out.println("~~RR");
            reindeerGroupList= new ArrayList<>();
            reindeersProccessBlockCondition.signalAll();
        } finally {
            GUI.presserRaindeersOnCSB();
            lock.unlock();
        }
    }
    
    /**
     * 
     * @param id 
     */
    public void AddReindeers(int id) {
//        GUI.presserReindeer(id);
        reindeersLock.lock();
        try {
            reindeerGroupList.add(id);
            GUI.presserReindeer(id);
//            System.out.println("Reindeer"+ id +" added to List");
            if(reindeerGroupList.size() == 9){
                GUI.jTextArea1TextChanger("Reindeer No"+id+" wants to enter CS");
                System.out.println("RW "+id);
                reindeersCriticalSection();
            } else {
                GUI.jTextArea1TextChanger("Reindeer No"+id+" wants to enter CS");
                System.out.println("RW "+id);
                reindeersProccessBlockCondition.await(); //block proccess to not going out CS
            }
        } catch (InterruptedException ex) {
        } finally {
            reindeersLock.unlock();
        }
    }
    
    /**
     * 
     * @param id
     */
    public void addElves(int id) {
        GUI.presserElve(id);
        GUI.jTextArea1TextChanger("Elve No"+id+" wants to enter CS");
        elvesLock.lock();
        try {
            elvesGroupList.add(id);
            GUI.presserElve(id);
//            GUI.presserElveT(id);
            if (elvesGroupList.size()==3) {
                GUI.jTextArea1TextChanger("Elve No"+id+" gets in the group of 3 to enter CS");
                System.out.println("EW "+id);
                elvesCriticalSection();
            } else {
                GUI.jTextArea1TextChanger("Elve No"+id+" gets in the group of 3 to enter CS");
                System.out.println("EW "+id);
                elvesProccessBlockCondition.await(); //block proccess to not going out CS
            }
//            if (elvesGroupList.size()>=3) {
//                if (!elvesGoingList.isEmpty()) {
//                    elvesWaitListCondition.await();
//                }
//                for (int i = 0; i < 3; i++) {
//                    elvesGoingList.add(elvesGroupList.get(0));
//                    elvesGroupList.remove(0);
//                }
//                elvesCriticalSection();
//            } else {
//                elvesProccessBlockCondition.await(); //block proccess to not going out CS
//            }
        } catch (InterruptedException ex) {
        }finally {
            elvesLock.unlock();
        }
    }
       
    
    
}
