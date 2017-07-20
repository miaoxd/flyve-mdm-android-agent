/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 * This file is part of flyve-mdm-android-agent
 *
 * flyve-mdm-android-agent is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Rafael Hernandez
 * @date      02/06/2017
 * @copyright Copyright © ${YEAR} Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android-agent
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

package org.flyve.mdm.agent.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class content some helpers function
 */
public class Helpers {

	/**
	 * private construtor
	 */
	private Helpers() {
	}

	/**
	 * Convert Base64 String in to plain String
	 * @param text String to convert
	 * @return String with a plain text
	 */
	public static String base64decode(String text) {
		String rtext = "";
		if(text == null) { return ""; }
		try {
			byte[] bdata = Base64.decode(text, Base64.DEFAULT);
			rtext = new String(bdata, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			FlyveLog.e(e.getMessage());
		}
		return rtext.trim();
	}

	/**
	 * Convert String in to Base64 encode
	 * @param text String to convert
	 * @return String with a base64 encode
	 */
	public static String base64encode(String text) {
		String rtext = "";
		if(text == null) { return ""; }
		try {
			byte[] data = text.getBytes("UTF-8");
			rtext = Base64.encodeToString(data, Base64.DEFAULT);
			rtext = rtext.trim().replace("==", "");
		} catch (UnsupportedEncodingException e) {
			FlyveLog.e(e.getMessage());
		}
		
		return rtext.trim();
	}

	/**
	 * Get Device Serial to work with simulator and real devices
	 * @return String with Device Serial
	 */
	public static String getDeviceSerial() {
		String serial;
		if(Build.SERIAL.equalsIgnoreCase("unknown")) {
			serial = "ABCDEFGHIJ1234568";
		} else {
			serial = Build.SERIAL;
		}

		return serial;
	}

	/**
	 * get Unix time
	 * @return int unix time
	 */
	public static int GetUnixTime() {
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();
		int utc = (int) (now / 1000);
		return (utc);
	}

	/**
	 * Open url on browser
 	 * @param context Context where is working
	 * @param url String the url to display
	 */
	public static void openURL(Context context, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(browserIntent);
	}

	public static String broadCastMessage(String type, String title, String body) {
		try {
			JSONObject json = new JSONObject();
			json.put("type", type);
			json.put("title", title);
			json.put("body", body);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDateTime = sdf.format(new Date());
			json.put("date", currentDateTime);

			return json.toString();

		} catch(Exception ex) {
			return null;
		}
	}
}