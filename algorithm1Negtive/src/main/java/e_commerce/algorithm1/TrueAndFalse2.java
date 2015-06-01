package e_commerce.algorithm1;

import java.util.ArrayList;
import java.util.List;

/***
 * 参考TrueAndFalse1，不用找到3X或者3O就可以开始运算
 * @author martin
 *
 */
public class TrueAndFalse2 implements ITrueAndFalse{
	
	static public class Cycle{
		private List<Integer> notProcess;
		private List<Integer> process;
		private int size;
		
		public Cycle(List<Integer> notProcess, List<Integer> process){
			this.notProcess = notProcess;
			this.process = process;
			this.size = notProcess.size() + process.size();
		}
		
		public int getLength(){ return this.size; }
		
		public String getFormat(){
			StringBuilder sb = new StringBuilder();
			for(int i : notProcess)
				sb.append(i>=0?String.format("+%d",i):i);
			for(int i : process)
				sb.append(i>=0?String.format("+%d",i):i);
			return sb.toString();
		}
	}

	static public class Processor{
		
		public Cycle execute(boolean[] source, int startOff){
			
			List<Integer> process = new ArrayList<Integer>();
			List<Integer> notProcess = new ArrayList<Integer>();
			int sum = 0;
			int idxOfSource = 0;
			int step = 1;
			this.lastX = startOff;
			this.bStart = false;
			boolean bStop = false;
			for(; idxOfSource<30 && startOff + idxOfSource < source.length && !bStop; idxOfSource++){
				step = source[startOff+idxOfSource]?step:-step;
				//System.out.print(step>0?String.format("+%d", step):step);
				this.bStart = this.bStart || step==-1;
				if(this.bStart){
					sum += step;
					process.add(step);
				} else
					notProcess.add(step);

				this.max = this.max<step?step:this.max;
				step = this.getNextStep(source, startOff, idxOfSource+1, sum);
				
				bStop = sum >= this.max;
			}
			//System.out.println(String.format("=%d", sum));
			return new Cycle(notProcess, process);
		}
		
		private boolean bStart = false;
		private int[] metaData = { 1,2,3,5,8,12,18,27,40,60,90,135,200,300,450,675,1000 };
		private int idxOfMetaData = 0;
		private int lastX = -1;
		private int max = 0;
		
		private int getMinStep(int sum, int step){

			int i=idxOfMetaData+1;
			if(sum+this.metaData[i] < step)
				return i;
			else{
				boolean bStop = false;
				for(i=idxOfMetaData; !bStop && i>=0; i--)
					bStop = sum + this.metaData[i] < step;
				if(bStop)
					return i+2;
				else
					return -1;
			}
		}
		
		private int getNextStep(boolean[] source, int startOff, int length, int sum){
			
			if(!bStart)
				return 1;
			else{
				
				if(this.metaData[this.idxOfMetaData] > 12){
					
					int idxOfMeta = this.getMinStep(sum, this.max);
					if(source[startOff + length-1])
						if(idxOfMeta>=0){
							this.idxOfMetaData = idxOfMeta;
							return this.metaData[this.idxOfMetaData];
						}else
							return this.metaData[++this.idxOfMetaData];
					
					if( startOff + length-this.lastX > 3 && (!source[startOff + length-1] && !source[startOff + length-2] && !source[startOff + length-3]) ){
						this.lastX = startOff + length-1;
						if(idxOfMeta>=0){
							this.idxOfMetaData = idxOfMeta;
							return this.metaData[this.idxOfMetaData];
						}else
							return this.metaData[++this.idxOfMetaData];
					}
					
					return this.metaData[this.idxOfMetaData];
					
				} else {
					if(source[startOff + length-1])
						return this.metaData[++this.idxOfMetaData];
					
					if( startOff + length-this.lastX >= 3 && (!source[startOff + length-1] && !source[startOff + length-2] && !source[startOff + length-3]) ){
						this.lastX = startOff + length-1;
						return this.metaData[++this.idxOfMetaData];
					}
				
					return this.metaData[this.idxOfMetaData];
				}
			}
		}
	}
	
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
	public IResult execute(int cycleStep){

		List<Cycle> result = new ArrayList<Cycle>();
		int startOff = 0;
		int length = -1;
		for(startOff=0; startOff<30 && length!=0; startOff+=length){
			Cycle cycle = new Processor().execute(this.source, startOff);
			result.add(cycle);
			length = cycle.getLength();
		}
		return new Result2(result);
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
