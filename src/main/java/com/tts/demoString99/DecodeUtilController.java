package com.tts.demoString99;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DecodeUtilController {

    static String TYPE_00 = "00";
    static String TYPE_21 = "21";
    static String TYPE_20 = "20";


    public static List<String>  decodeOne(String  trx){
        System.out.println("lanzando funcion recivedecode");
        String hex = trx.substring(1, 2);
        String TYPE= decodeToHexString(hex);
        System.out.println("==>hex: " + hex + " res: " + TYPE );
        List<String> filas = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(trx);

        while (matcher.find()) {
            filas.add(matcher.group(1));
        }

        return procesFilas(filas);
    }

    public static List<String> decodeAll(List<String> list){
        System.out.println("lanzando funcion recivedecode");

        List<String> fileAll = new ArrayList<>();

        for (String trx : list) {
            String hex = trx.substring(1, 2);
            String TYPE= decodeToHexString(hex);
            System.out.println("==>hex: " + hex + " res: " + TYPE );
            List<String> filas = new ArrayList<>();
            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(trx);

            while (matcher.find()) {
                filas.add(matcher.group(1));
            }


            //filas.forEach(System.out::println);
            List<String> filasDecode = procesFilas(filas);
            String resultado = filasDecode.stream()
                    .map(item -> "[" + item + "]")
                    .collect(Collectors.joining(" "));

            resultado = "<<" + resultado + ">>";
            //List<String> coldDecode = procesCols(cols);
            /*
            if (TYPE_00.equals(TYPE)){

            }

            if (TYPE_21.equals(TYPE)){

            }

            if (TYPE_20.equals(TYPE)){
            }*/

            fileAll.add(resultado);



        }
        return fileAll;

    }

    public static List<String> procesFilas(List<String> filas) {
        List<String> filasDecode = new ArrayList<>();
        for (String fila : filas) {
            List<String> split = Arrays.asList(fila.split(":"));
            String colsDecodeCompact =String.join(",", procesCols(split));
            System.out.print("cols: " + colsDecodeCompact+" ");
            filasDecode.add(colsDecodeCompact);
        }
        System.out.println();
        return filasDecode;
    }

    public static List<String>  procesCols(List<String> cols) {
        List<String> colsDecode = new ArrayList<>();

        for (String col : cols) {
            String colDecode = decodeToHexString(col);
            colsDecode.add(colDecode);

        }

        return colsDecode;

    }

    public static String decodeToHexString(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        for (byte b : bytes) {
            int value = b & 0xFF;

            // LF -> salto de línea
            if (value == 0x0A) {
                result.append(System.lineSeparator());
                continue;
            }

            // CR -> se ignora
            if (value == 0x0D) {
                continue;
            }

            // Siempre HEX (sin espacio, como en tu else original)
            result.append(String.format("%02X", value));
        }

        return result.toString();
    }

}
