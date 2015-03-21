package jeckelcorelibrary.api.processes;

public interface IProcessCycleHandler
{
	public void handleCycleBegin();

	public void handleCycleEnd();

	public void handleCycleCancel();

	public void handleCycleReset();
}
