package e_commerce.algorithm1;

import java.util.List;

public class ResultNull {
	
	private List<Integer> source;
	private int sum;
	private int max;
	private boolean stop;
	private int countOfCycle;
	
	public List<Integer> getSource(){ return this.source; }
	public boolean getStop(){ return this.stop; }
	
	public int getMaxCycleStep(){ return this.max; }
	public int getCountOfCycle(){ return this.countOfCycle; }

}
