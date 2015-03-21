package jeckelcorelibrary.api.tiles;

import java.util.List;

import jeckelcorelibrary.api.processes.ITickProcess;

public interface ITileProcessor
{
	public List<ITickProcess> getProcesses();
}
