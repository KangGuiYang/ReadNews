package com.allen.readews.utils;

import android.util.Log;

public class ToHtmlString {
	protected static int textSizeInt;

	/**
	 * 格式化内容
	 * 
	 * @param in
	 * @return
	 */
	public static String toHTMLString(String in) {
		if ("".equals(in)) {
			return in;
		}
		String p = "</p><p>";

		StringBuffer out = new StringBuffer();

		int index = in.indexOf("</style");
		String styleStr = "";
		if (index > -1) {
			styleStr = in.substring(0, index);

			Log.d("   styleStr=", styleStr);
		}
		int tdIndex = in.indexOf("<td>");
		if (tdIndex > -1) {
			return in;
		}

		switch (textSizeInt) {
		case 0:
			if (index > -1) {
				out.append(styleStr
						+ "p{ line-height:1.5; font-size:17px;text-indent:2em;}</style>");
				// out.append("") ;
			} else
				out.append("<style> p{ line-height:1.5; font-size:17px;text-indent:2em;}</style>");
			break;
		case 1:
			if (index > -1) {
				// out.append("") ;
				out.append(styleStr
						+ "p{ line-height:1.5; font-size:20px;text-indent:2em;}</style>");
			} else
				out.append("<style>p{ line-height:1.5; font-size:20px;text-indent:2em;}</style>");
			break;
		case 2:
			if (index > -1) {
				out.append(styleStr
						+ " p{ line-height:1.5; font-size:24px;text-indent:2em;}</style>");
			} else
				out.append("<style>p{ line-height:1.5; font-size:24px;text-indent:2em;}</style>");
			break;
		}

		// if(index>-1){
		// Logger.d("before   in =="+in ) ;
		// int length = "<style>".length() ;
		// in = in.substring(6,in.length()) ;
		// Logger.d("after   in =="+in ) ;
		// }

		out.append("<p>");

		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'')
				out.append("'");
			else if (c == '\"')
				out.append("\"");
			// else if (c == '<')
			// out.append("&lt;");
			// else if (c == '>')
			// out.append("&gt;");
			// else if (c == '&')
			// out.append("&amp;");
			else if (c == '\n')
				out.append(p);
			else if (c == '\r')
				out.append(p);
			else
				out.append(c);
		}

		return out.toString();
	}
}
