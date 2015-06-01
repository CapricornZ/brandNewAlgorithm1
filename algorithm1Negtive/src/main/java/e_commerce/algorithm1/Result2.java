package e_commerce.algorithm1;

import java.util.List;

public class Result2 implements IResult{

	private List<TrueAndFalse2.Cycle> cycles;
	public Result2(List<TrueAndFalse2.Cycle> cycles){
		this.cycles = cycles;
	}
	
	@Override
	public String getFormated() {
		
		StringBuilder sb = new StringBuilder();
		for(TrueAndFalse2.Cycle cycle : cycles){
			sb.append(cycle.getFormat());
			sb.append("|");
		}
		return sb.toString();
	}
	
	public int getMaxCycleStep(){ return 0; }
	public int getCountOfCycle(){ return 0; }
}
