package com.jda.snew.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class HTTPClient {

	public static String postRequest(String url, String rawBody) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		StringBuilder result = null;

		try {
			StringEntity entity = new StringEntity(rawBody);
			post.setEntity(entity);
			response = client.execute(post);

			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return result.toString();
	}

	public static String getBody(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		StringBuilder result = null;
		try {
			response = httpclient.execute(httpget);
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				// Ignore
			}
		}
		return result.toString();
	}
}
