package keter.util;

import java.io.PrintWriter;

/**
 * Utility methods for generating simple HTML pages.
 * 
 * @author Maciej Hamiga
 */
public class HtmlUtil {

	public static void writeErrorPageHeaders(PrintWriter writer, String title) {
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>" + title + "</title>");
		writer.println("</head>");
		writer.println("<body>");
	}

	public static void writeErrorPageFooters(PrintWriter writer) {
		writer.println("</body>");
		writer.println("</html>");
	}

}
