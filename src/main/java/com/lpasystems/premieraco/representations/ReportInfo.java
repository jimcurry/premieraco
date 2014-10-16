package com.lpasystems.premieraco.representations;

/**
 * Used to report information needed to run Cognos reports via the web app
 * 
 * @author Jim
 * 
 */
public class ReportInfo {
	private final String reportCode;

	private final String reportId;

	/**
	 * Creates an instance of the class using the passed in reportId and
	 * reportCode
	 * 
	 * @param id
	 * @param reportId
	 */
	public ReportInfo(String reportId, String reportCode) {
		this.reportCode = reportCode;
		this.reportId = reportId;
	}

	/**
	 * @return the reportCode
	 */
	public String getReportCode() {
		return reportCode;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

}
