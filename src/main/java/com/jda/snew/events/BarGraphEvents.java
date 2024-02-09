package com.jda.snew.events;

public class BarGraphEvents {

	private DataSet datasets;

	public void setDataSet(int[] values, String[] labels) {
		if(datasets == null) {
			datasets = new DataSet();			
		}
		datasets.setLabels(labels);
		datasets.setValues(values);
	}
	public DataSet getDataSet() {
		return this.datasets;
	}

	public static final class DataSet {

		private int[] values;
		private String[] labels;
		private final String color = "blue";
		
		public int[] getValues() {
			return values;
		}

		public void setValues(int[] values) {
			this.values = values;
		}

		public String[] getLabels() {
			return labels;
		}

		public String getColor() {
			return color;
		}

		public void setLabels(String[] labels) {
			this.labels = labels;
		}
	}
}
