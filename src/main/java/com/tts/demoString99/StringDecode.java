package com.tts.demoString99;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringDecode {

    private static final Charset LATIN_1 = Charset.forName("ISO-8859-1");

    public static void hexhex() {
        File input = new File("decodificado.txt");
        File output = new File("rec.txt");

        try (
                BufferedReader br = new BufferedReader(new FileReader(input));
                BufferedWriter bw = new BufferedWriter(new FileWriter(output))) {

            String line;
            while ((line = br.readLine()) != null) {
                StringBuilder result = new StringBuilder();

                for (int i = 0; i < line.length() - 1; i += 2) {
                    String hex = line.substring(i, i + 2);

                    if (!isValidHex(hex)) {
                        result.append(hex);
                        continue;
                    }

                    int value = Integer.parseInt(hex, 16);
                    char ch = new String(new byte[] { (byte) value }, LATIN_1).charAt(0);

                    if (isPrintable(ch)) {
                        result.append(ch);
                    } else {
                        result.append(hex);
                    }
                }

                bw.write(result.toString());
                bw.newLine();
            }

            System.out.println("Archivo rec.txt generado correctamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidHex(String hex) {
        return hex.matches("[0-9A-Fa-f]{2}");
    }

    private static boolean isPrintable(char c) {

        // Letras A-Z a-z
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            return true;
        }

        // Números
        if (c >= '0' && c <= '9') {
            return true;
        }

        // Vocales con tilde
        switch (c) {
            case 'á': case 'é': case 'í': case 'ó': case 'ú':
            case 'Á': case 'É': case 'Í': case 'Ó': case 'Ú':
                return true;
        }

        // Símbolos permitidos
        return c == '\'' || c == '"' || c == ',' || c == ':' || c == '.';
    }




    /**
     * guardar en fichero lo decodifcado.
     * 
     * @param inputPath
     * @param outputPath
     * @throws IOException
     */
    public static void readBinaryFile(String inputPath, String outputPath, boolean todoHex) throws IOException {

        try (
                InputStream in = new FileInputStream(inputPath);
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(outputPath, false), // replace siempre
                                StandardCharsets.UTF_8))) {

            int b;

            while ((b = in.read()) != -1) {
                int value = b & 0xFF;

                // LF -> salto de línea
                if (value == 0x0A) {
                    writer.newLine();
                    continue;
                }

                // CR -> se ignora
                if (value == 0x0D) {
                    continue;
                }

                String characterDecode = ""; // String.format("%02X", value);

                if (!todoHex) {
                    characterDecode = String.format("%02X ", value);

                    characterDecode = characterDecode.toUpperCase().replace("3A", ":");
                    characterDecode = characterDecode.toUpperCase().replace("22", "\"");
                } else {
                    characterDecode = String.format("%02X", value);

                }

                writer.write(characterDecode);
            }

            writer.flush();
        }
    }

    public static void decodeTrxString99(
            String inputPath,
            String outputPath,
            int targetLine) throws IOException {

        StringBuilder decodedLine = new StringBuilder();

        try (
                InputStream in = new FileInputStream(inputPath);
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(outputPath, false),
                                StandardCharsets.UTF_8))) {

            int b;
            int currentLine = 1;
            boolean capturing = (targetLine == 1);

            while ((b = in.read()) != -1) {
                int value = b & 0xFF;

                // LF -> fin de línea
                if (value == 0x0A) {
                    if (capturing) {
                        break; // línea completa
                    }
                    currentLine++;
                    capturing = (currentLine == targetLine);
                    continue;
                }

                // CR -> ignorar
                if (value == 0x0D) {
                    continue;
                }

                // Acumular solo la línea requerida
                if (capturing) {
                    decodedLine.append(String.format("%02X", value));
                }
            }

            /*
             * =============================
             * ZONA DE PROCESAMIENTO
             * =============================
             */

            String lineaHex = decodedLine.toString().trim();

            // 🔧 aquí haces tus cálculos / validaciones / modificaciones
            // ejemplo:
            // lineaHex = lineaHex.replace("FF", "00");
            lineaHex = lineaHex.toUpperCase().replace("222C22", "\n");
            lineaHex = lineaHex.toUpperCase().replace("22", "");
            lineaHex = lineaHex.toUpperCase().replace("3A", ",");

            /*
             * =============================
             * ESCRITURA FINAL
             * =============================
             */

            writer.write(lineaHex);
            writer.newLine();
            writer.flush();
        }
    }

}
