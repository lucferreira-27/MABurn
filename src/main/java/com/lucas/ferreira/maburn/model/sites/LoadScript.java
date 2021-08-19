package com.lucas.ferreira.maburn.model.sites;

public class LoadScript {

	private Execute execute = new Execute();
	private ExecuteValues lastExecuteValues;
	private InitScript data = new InitScript();

	public void execute(ExecuteValues executeValues) {
		System.out.println("execute");
		if (executeValues.getParameter() == null || executeValues.getParameter().isEmpty())
			execute.execute(executeValues);
		else
			execute.execute(executeValues);
		this.lastExecuteValues = executeValues;
	}

	public void repeatExecute() {
		System.out.println("repeatExecute");
		execute(lastExecuteValues);
	}

	public String getScript() {
		return data.script;
	}
	
	public Execute getExecute() {
		return execute;
	}

}
