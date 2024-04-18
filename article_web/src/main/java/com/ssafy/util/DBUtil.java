package com.ssafy.util;

import org.springframework.stereotype.Component;

@Component
public class DBUtil {
	// DataSource는 META-INF/context.xml
	public void close(AutoCloseable... autoCloseables) {
		for (AutoCloseable ac : autoCloseables) {
			if (ac != null) {
				try {
					ac.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
