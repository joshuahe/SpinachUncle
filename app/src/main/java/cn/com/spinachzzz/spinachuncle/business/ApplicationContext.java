package cn.com.spinachzzz.spinachuncle.business;

/**
 * Created by Jing on 12/03/15.
 */
public class ApplicationContext {

    public static ApplicationContext instance;

    private TasksService tasksService;

    private ApplicationContext() {

        tasksService = new TasksService();
    }

    public synchronized static ApplicationContext getInstance() {

        if (instance == null) {
            instance = new ApplicationContext();
        }

        return instance;
    }

    public TasksService getTasksService() {
        return tasksService;
    }
}
