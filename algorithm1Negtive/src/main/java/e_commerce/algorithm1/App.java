package e_commerce.algorithm1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import e_commerce.algorithm1.App;
import ecommerce.algorithm1.pairs.PairNegtive;
import pairs.IPair;
import process.IProcessor;
import process.Start;

public class App {
	
	public static int cycleStep = 6;
	public static String Class3O = "process.CycleNegtive";
	public static String Class3X = "process.CyclePositive";
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private static IPair pairEngine;
	private static int[] sourceStep3 = new int[] { 1, 2, 3, 5, 8, 13, 21, 34,
			55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946,
			17711, 28657, 46368 };

	public static void main(String[] args) throws IOException {

		String file = args[0];
		String output = args[1];
		String fileName = null;
		{
			String[] array = file.split("/");
			fileName = array[array.length-1];
			array = fileName.split("\\.");
			fileName = String.format("%s%s_step%d.txt", output, array[0], cycleStep);
		}
		FileOutput.init(fileName);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		int[] addupX = new int[100];
		int[] addupO = new int[100];
		int seq_x_max = 0, seq_o_max = 0;
		List<Integer> maxSteps = new ArrayList<Integer>();
		List<Integer> countOfCycles = new ArrayList<Integer>();
		ecommerce.algorithm1.pairs.IPair pairEngine = new PairNegtive();
		while ((lineTxt = bufferedReader.readLine()) != null) {

			String strSource = lineTxt;
			
			//SourceRow sourceRow = new SourceRow(strSource, pairEngine);
			SourceRow3 sourceRow = new SourceRow3(strSource, pairEngine);
			ITrueAndFalse taf = sourceRow.execute();
			//List<e_commerce.algorithm1.Result> results = new ArrayList<e_commerce.algorithm1.Result>();
			
			System.out.println(strSource);
			FileOutput.writeline(strSource);
			System.out.println(taf.getFormated());
			FileOutput.writeline(taf.getFormated());
			
			IResult r = taf.execute(App.cycleStep);
			System.out.println(r.getFormated());
			FileOutput.writeline(r.getFormated());
			
			maxSteps.add(r.getMaxCycleStep());
			countOfCycles.add(r.getCountOfCycle());
			
			//maxSteps.add(r.getMaxCycleStep());
			//countOfCycles.add(r.getCountOfCycle());
			
			/*
			int header = step1(arrSource);
			boolean[] result = step2(strSource, header);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < header + 1; i++){
				logger.debug("-");
				sb.append("-");
			}
			FileOutput.write(sb.toString());
			
			sb = new StringBuilder();
			boolean last = result[0];
			int count = 1;
			int countTrue = 0, countFalse = 0;
			if(last){
				countTrue ++ ;
				logger.debug("o");
				sb.append("o");
			} else {
				countFalse ++ ;
				logger.debug("x");
				sb.append("x");
			}
			
			for(int i=1; i<result.length; i++){
				if (result[i]) {
					countTrue++;
					logger.debug("o");
					sb.append("o");
				} else {
					countFalse++;
					logger.debug("x");
					sb.append("x");
				}
				
				if(result[i] == last){
					count ++ ;
				} else {
					if(last){
						addupO[count-1]++;
						seq_o_max = seq_o_max>count?seq_o_max:count;
					}
					else{
						addupX[count-1]++;
						seq_x_max = seq_x_max>count?seq_x_max:count;
					}
					last = result[i];
					count = 1;
				}
			} FileOutput.write(sb.toString());

			logger.debug(" [ x:{} ({}%), o:{} ({}%) ]\r\n", 
					countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
					countTrue, ((float)countTrue*100/(float)(countFalse+countTrue)));
			logger.debug("{}\r\n", strSource);
			FileOutput.write(String.format(" [ x:%d (%f%%), o:%d (%f%%) ]\r\n",
					countFalse, ((float)countFalse*100/(float)(countFalse+countTrue)), 
					countTrue, ((float)countTrue*100/(float)(countFalse+countTrue))));
			FileOutput.write(String.format("%s\r\n", strSource));

			int maxStep = step3x(result);
			maxSteps.add(maxStep);
			*/
		}
		
		int max = seq_x_max>seq_o_max?seq_x_max:seq_o_max;
		for(int i=0; i<max; i++){
			logger.debug("SEQ {} : {x:{}, o:{}}\r\n", i+1, addupX[i], addupO[i]);
		}
		
		int sumOfMax = maxSteps.size();
		HashMap<Integer, Integer> maxStepMap = new HashMap<Integer, Integer>();
		for(int maxStep : maxSteps){
			if(null == maxStepMap.get(maxStep))
				maxStepMap.put(maxStep, 1);
			else
				maxStepMap.put(maxStep, maxStepMap.get(maxStep)+1);
		}
		for(Map.Entry entry : maxStepMap.entrySet()){
			logger.debug("MAX {} : {} [{}%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfMax);
			FileOutput.write(String.format("MAX %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfMax));
		}
		
		int sumOfCycle = countOfCycles.size();
		HashMap<Integer, Integer> cycleMap = new HashMap<Integer, Integer>();
		for(int countOfCycle : countOfCycles){
			if(null == cycleMap.get(countOfCycle))
				cycleMap.put(countOfCycle, 1);
			else
				cycleMap.put(countOfCycle, cycleMap.get(countOfCycle)+1);
		}
		for(Map.Entry entry : cycleMap.entrySet()){
			logger.debug("COUNT {} : {} [{}%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfCycle);
			FileOutput.write(String.format("COUNT %d : %d [%f%%]\r\n", entry.getKey(), entry.getValue(), (float)((Integer)entry.getValue()*100)/(float)sumOfCycle));
		}
		
		read.close();
		FileOutput.close();
		
		/*boolean[] source = new boolean[]{false,false,false,false,false,false,false,true,true,false,true};
		step3x(source);*/
	}

	public static boolean pair(String first, String second) {
		return pairEngine.pair(first, second);
	}
	
	public static int step3x(boolean[] source){
		
		IProcessor processor = Start.findProcessor(source);
		if(null != processor){
			processor.execute();
			return processor.getMaxStep();
		}
		return 0;
	}

	public static void step3(boolean[] source, int length) {
		int sum = 0;
		int indexSourceStep3 = 0;
		int max = 0;
		boolean foundMax = false;
		for (boolean e : source){
			if( max < sourceStep3[indexSourceStep3] && !foundMax){
				max = sourceStep3[indexSourceStep3];
			}
			
			if (e) {
				sum += sourceStep3[indexSourceStep3];
				logger.debug("+{}", sourceStep3[indexSourceStep3]);
				if (indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= sourceStep3[indexSourceStep3];
				logger.debug("-{}", sourceStep3[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
			
			if(sum >= 10)
				foundMax = true;
		}
		/*for (int indexSource = length + 1; indexSource < source.length; indexSource++) {
			if (source[indexSource]) {
				sum += sourceStep3[indexSourceStep3];
				logger.debug("+{}", sourceStep3[indexSourceStep3]);
				if (indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= sourceStep3[indexSourceStep3];
				logger.debug("-{}", sourceStep3[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
		}*/
		logger.debug(" = {} [ MAX: {} ]\r\n", sum, max);
	}

	public static boolean[] step2(String source, int length) {
		
		boolean[] rtn = new boolean[source.length()-length-1];
		//boolean[] rtn = new boolean[source.length()];
		int index = 1;
		while (true) {

			int i = 0;
			boolean isPair = false;

			for (; i < 4 && !isPair
					&& index + length + (i + 1) < source.length(); i++) {
				String first = source.substring(index, index + (i + 1));
				String second = source.substring(index + length, index + length
						+ (i + 1));
				isPair = pair(first, second);
				//rtn[index + length + i] = isPair;
				rtn[index -1 + i] = isPair;
			}
			if (i == 0)
				break;
			index += i;
		}

		return rtn;
	}

	public static int step1(char[] array) {

		int rtn = 1;
		int column = 0;
		int COLUMN = 4;
		char last = array[0]; // System.out.print(last);
		for (int index = 1; index < array.length && column < COLUMN; index++) {

			if (array[index] != last) {
				last = array[index];
				column++;
			}

			if (column <= COLUMN - 1)
				rtn++;
		}
		return rtn;
	}
}
