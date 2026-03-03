package com.tts.demoString99;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // For local development testing
public class WebController {

    // In-memory state as requested for the demo
    private List<String> loadedFileLines = new ArrayList<>();

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Por favor seleccione un archivo.");
            return ResponseEntity.badRequest().body(response);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            loadedFileLines.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                loadedFileLines.add(line);
            }

            //DecodeUtilController.decodeAll(loadedFileLines);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Archivo cargado exitosamente en memoria.");
            response.put("totalLines", loadedFileLines.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al leer el archivo: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/decode-all")
    public ResponseEntity<List<String>> decodeAll() {
        // As requested, returns each line as a simple string.
        // Later this will use StringDecode logic, but for now we just return the lines
        // for the demo.
        List<String> decodedLines = DecodeUtilController.decodeAll(loadedFileLines);
        return ResponseEntity.ok(decodedLines);
    }

    @GetMapping("/decode-line")
    public ResponseEntity<?> decodeSpecificLine(
            @RequestParam(value = "lineNumber", required = false) Integer lineNumber) {
        // In the final version, you would pass the index or line to StringDecode.
        // For the demo: "para simualr la tabla haz una lizta de array que luego haga
        // split por la coma"
        // Return 30 columns for a dynamic number of rows (e.g., all lines split by
        // comma)

        if (loadedFileLines.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No hay archivo cargado en memoria.");
            return ResponseEntity.badRequest().body(response);
        }

        List<String[]> decodedLines = new ArrayList<>();
        List<String> lines = DecodeUtilController.decodeOne(loadedFileLines.get(lineNumber));

        System.out.println("lines: " + lines );
        for (String line : lines) {
            String[] padded = splitAndPad(line);
            decodedLines.add(padded);
        }




        return ResponseEntity.ok(decodedLines);
    }

    private String[] splitAndPad(String line) {
        String[] parts = line.split(",");
        String[] padded = new String[30]; // Fixed 30 columns as requested
        for (int i = 0; i < 30; i++) {
            if (i < parts.length) {
                padded[i] = parts[i].trim();
            } else {
                padded[i] = ""; // Pad the rest of the 30 columns
            }
        }
        return padded;
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearMemory() {
        loadedFileLines.clear();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Memoria limpiada de vuelta al estado original.");
        return ResponseEntity.ok(response);
    }
}
