package application;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import utility.IDAssigner;

public class ThreadPool extends ThreadGroup {
	
	private static IDAssigner poolId = new IDAssigner(1);
	private boolean alive;
	private List<Runnable> taskQueue;
	private int id;

	public ThreadPool(int threadsNumber) {
		super("ThreadPool-" + poolId.next());
		this.id = poolId.getCurrentId();
		setDaemon(true);
		taskQueue = new LinkedList<>();
		alive = true;
		for(int i = 0; i < threadsNumber; i++) {
			new PooledThread(this).start();
		}
	}
	
	public synchronized void runTask(Runnable task) {
		if(!alive)
			throw new IllegalStateException("ThreadPool-" + id + " is dead");
		
		if(task != null) {
			taskQueue.add(task);
			notify();
		}
	}

	protected synchronized Runnable getTask() throws InterruptedException {
		while(taskQueue.size() == 0) {
			if(!alive)
				return null;
			wait();
		}
		
		return taskQueue.remove(0);
	}
	
	public synchronized void close() {
		if(!alive)
			return;
		alive = false;
		taskQueue.clear();
		interrupt();
	}
	
	public void join() {
		synchronized(this) {
			alive = false;
			notifyAll();
		}
		
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);
		for(int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				Logger.getLogger(PooledThread.class.getName()).log(Level.SEVERE, null, e);
//				e.printStackTrace();
			}
		}
	}

}
