package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    public byte[] generateSpeech(String text) {
        try {
            String apiUrl = "http://localhost:5500/api/tts?voice=en-us/ljspeech&text=" +
                    URLEncoder.encode(text, "UTF-8");

            URI uri = new URI(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream input = connection.getInputStream();

            input.transferTo(output);

            return output.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}