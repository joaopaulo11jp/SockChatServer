package br.edu.ifpb.sockchat.tasks;

public class TaskWorker implements Runnable{
    private Task task;	
	
	public TaskWorker(Task task){
		this.task = task;
	}
	
	@Override
	public void run() {
		task.doTask();
	}

}
