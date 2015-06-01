package e_commerce.algorithm1;

import ecommerce.algorithm1.processor.IProcessor;
import ecommerce.algorithm1.processor.Processor3O;
import ecommerce.algorithm1.processor.Processor3OChange;
import ecommerce.algorithm1.processor.Processor3X;
import ecommerce.algorithm1.processor.Processor3XChange;

/***
 * 参考TrueAndFalse1，不用找到3X或者3O就可以开始运算
 * @author martin
 *
 */
public class TrueAndFalse2 implements ITrueAndFalse {

	private boolean[] source;
	private int header;
	private String type;
	public TrueAndFalse2(boolean[] source, int header){
		this.source = source;
		this.header = header;
	}
	
	@Override
	public boolean[] getSource(){ return this.source; }
	
	@Override
	public String getType() { return this.type; }

	@Override
	public Result execute(int cycleStep) {
		
		String class3O = "ecommerce.algorithm1.processor.CycleNegtive";
		String class3X = "ecommerce.algorithm1.processor.CyclePositive";
		boolean bStop = false;

		IProcessor processor = null;
		if("Processor3X".equals(App.classProcessor))
			//processor = new Processor3X(this.source, 0, cycleStep, class3X);
			processor = new Processor3XChange(this.source, 0, cycleStep, class3X, class3O);
		else
			//processor = new Processor3O(this.source, 0, cycleStep, class3O);
			processor = new Processor3OChange(this.source, 0, cycleStep, class3O, class3X);
		bStop = processor.execute();
		this.type = processor instanceof Processor3O ? "NEGTIVE" : "POSITIVE";
		return new Result(processor.getProcedure(), processor.getMaxStep(), bStop, processor.getCountOfCycle());
	}
	
	@Override
	public String getFormated(){
		
		int countFalse = 0, countTrue = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<=this.header; i++)
			sb.append("-");
		
		for(boolean val : source){
			if(val){
				sb.append("o");
				countTrue ++ ;
			} else {
				sb.append("x");
				countFalse ++ ;
			}
		}
		sb.append(String.format(" [x:%d (%f%%), o:%d (%f%%)]", 
				countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
		return sb.toString();
	}
}
