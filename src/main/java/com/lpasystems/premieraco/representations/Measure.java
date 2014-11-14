package com.lpasystems.premieraco.representations;

import java.math.BigDecimal;
import java.util.List;

/**
 * Holds the information about performance measures
 * 
 * @author Jim
 * 
 */
public class Measure {

	public static class Range {
		private final String rangePercentileName;

		private final BigDecimal rangeStartMeasureValue;

		/**
		 * @param rangeStartMeasureValue
		 * @param rangePercentileName
		 */
		public Range(BigDecimal rangeStartMeasureValue, String rangePercentileName) {
			this.rangeStartMeasureValue = rangeStartMeasureValue;
			this.rangePercentileName = rangePercentileName;
		}

		/**
		 * @return the rangePercentilName
		 */
		public String getRangePercentileName() {
			return rangePercentileName;
		}

		/**
		 * @return the rangeStartMeasureValue
		 */
		public BigDecimal getRangeStartMeasureValue() {
			return rangeStartMeasureValue;
		}
	}

	private final String measureCode;

	private final String measureGroupCode;

	private final String measureTypeCode;

	private final String reverseScoreInd;
	
	private final List<Range> ranges;

	/**
	 * @param measureCode
	 * @param measureGroupCode
	 * @param measureTypeCode
	 * @param reverseScoreInd
	 * @param ranges
	 */
	public Measure(String measureCode, String measureGroupCode, String measureTypeCode, String reverseScoreInd, List<Range> ranges) {
		this.measureCode = measureCode;
		this.measureGroupCode = measureGroupCode;
		this.measureTypeCode = measureTypeCode;
		this.reverseScoreInd = reverseScoreInd;
		this.ranges = ranges;
	}

	/**
	 * @return the measureCode
	 */
	public String getMeasureCode() {
		return measureCode;
	}

	/**
	 * @return the measureGroupCode
	 */
	public String getMeasureGroupCode() {
		return measureGroupCode;
	}

	/**
	 * @return the measureTypeCode
	 */
	public String getMeasureTypeCode() {
		return measureTypeCode;
	}

	/**
	 * @return the ranges
	 */
	public List<Range> getRanges() {
		return ranges;
	}

	/**
	 * @return the reverseScoreInd
	 */
	public String getReverseScoreInd() {
		return reverseScoreInd;
	}

}