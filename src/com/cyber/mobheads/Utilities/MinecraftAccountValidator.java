/*    */
package com.cyber.mobheads.Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MinecraftAccountValidator {

	public static boolean isValidPlayer(String playername) {

		try {

			String urlS = "https://api.mojang.com/users/profiles/minecraft/" + playername;


			URL url = new URL(urlS);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();

			JsonParser jp = new JsonParser();
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject rootobj = root.getAsJsonObject();

			return true;

		} catch (IOException ex) {

			return false;

		}

	}

}
