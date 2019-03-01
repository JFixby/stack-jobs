
package com.jfixby.sofj.go;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.strings.Strings;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.red.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.red.log.SimpleFileLog;
import com.jfixby.scarabei.red.log.SimpleLogger;

public class ReadRss {

	public static void main (final String[] args) throws IOException {

		ScarabeiDesktop.deploy();
		L.deInstallCurrentComponent();
		final File outputFolder = LocalFileSystem.ApplicationHome().child("output");
		final File logFile = outputFolder.child("log.txt");
		L.replaceComponent(new SimpleLogger(new SimpleFileLog(logFile)));

		SystemSettings.setExecutionMode(ExecutionMode.PUBLIC_RELEASE);

		final File inputFolder = LocalFileSystem.ApplicationHome().child("input");

		final File rssFile = inputFolder.child("rss.txt");
		final String data = rssFile.readToString();

		final List<String> list = Strings.split(data, "<category>");
		final Map<String, Integer> counts = Collections.newMap();
		counts.setDefaultValue(0);

		for (final String s : list) {
			final int pos = s.indexOf("</category>");
			if (pos == -1) {
				continue;
			}
			final String cat = s.substring(0, pos);
			Integer count = counts.get(cat);
			count++;
			counts.put(cat, count);

		}
// L.d("categories", counts);

		final List<Category> categories = Collections.newList();

		for (final String k : counts.keys()) {
			final Category c = new Category();
			c.category = k;
			c.value = counts.get(k);
			categories.add(c);
		}

		categories.sort();

		L.d("ranking", categories);

	}

}
