package com.agglotek.insidesales.emailservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TemplateLoader {
    private final String basePath;
    private final Map<String, String> templateCache;
    private final boolean isRemoteTemplates;

    public TemplateLoader(String basePath) {
        this.basePath = basePath;
        this.templateCache = new ConcurrentHashMap<>();
        this.isRemoteTemplates = basePath.startsWith("http");
    }

    /**
     * Load template from Bluehost server via HTTP
     */
    String loadRemoteTemplate(String templateName) throws IOException {
        String templateUrl = basePath + "/" + templateName + ".html";

        try {
            URL url = new URL(templateUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();

                return content.toString();
            } else {
                throw new IOException("Failed to load template: " + templateName +
                        " (HTTP " + responseCode + ")");
            }
        } catch (Exception e) {
            throw new IOException("Error loading remote template: " + templateName, e);
        }
    }

    public String getTemplate(String templateName, Map<String, String> variables) throws IOException {
        String template;

        // Check cache first
        if (templateCache.containsKey(templateName)) {
            template = templateCache.get(templateName);
        } else {
            if (isRemoteTemplates) {
                template = loadRemoteTemplate(templateName);
            } else {
                String path = basePath + "/" + templateName + ".html";
                template = Files.readString(Paths.get(path));
            }
            templateCache.put(templateName, template);
        }

        // Replace placeholders in template with actual values
        if (variables != null) {
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
        }

        return template;
    }

}
