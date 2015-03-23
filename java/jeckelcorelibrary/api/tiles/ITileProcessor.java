package jeckelcorelibrary.api.tiles;

import java.util.List;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.core.guis.SynchList;

public interface ITileProcessor
{
	public List<ITickProcess> getProcesses();

	public void supplySynchHandlers(SynchList list);
}
