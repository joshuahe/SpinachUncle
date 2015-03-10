package cn.com.spinachzzz.spinachuncle.service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskPool {

    private static TaskPool instance = null;

    public static synchronized TaskPool getInstance() {
	if (instance == null)
	    instance = new TaskPool();
	return instance;
    }

    private ThreadPoolExecutor threadPool;

    private TaskPool() {
	threadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
		new LinkedBlockingQueue<Runnable>(),
		new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void submit(Runnable task) {
	threadPool.submit(task);
    }

}
