package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import utility.IDAssigner;

public class PooledThread extends Thread {
	
	private static IDAssigner idAssigner = new IDAssigner(1);
	private ThreadPool pool;
	
	public PooledThread(ThreadPool pool) {
		super(pool, "PooledThread-" + idAssigner.next());
		this.pool = pool;
	}
	
	@Override
	public void run() {
		while(!isInterrupted()) {
			Runnable task = null;
			try {
				task = pool.getTask();
			} catch (InterruptedException e) {
				Logger.getLogger(PooledThread.class.getName()).log(Level.SEVERE, null, e);
//				e.printStackTrace();
			}
			
			if(task == null)
				return;
			
			try {
				task.run();
			}
			catch(Throwable t) {
				pool.uncaughtException(this, t);
			}
		}
	}

}
