package x21u025.web.dto;

import lombok.Data;

@Data
public class YNChartDto {

	private int id;
	private String text;
	private int weight;
	private int[] bgId;
	private int sort;
	private boolean deleteFlag;

}
