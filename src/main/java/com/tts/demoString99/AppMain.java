package com.tts.demoString99;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppMain implements ApplicationRunner {
    // @Autowired
    // private MailService mailService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // MailClase.sendMailAlert("123456");
        //System.out.println("enviado mail...");
        // System.out.println("Iniciando prueba de lectura de JSON...");
        /**
         * "=22
         * ,=2c
         * :=3a
         * logica para decodificar
         * leer el archivo en bytes y pasar a hex
         * para separar en filas remplazar los equivales a "," que son en hex 222C22 =
         * \n
         * separar en columans cada fila =rempalzar 3a = ,
         */

        /** decodifcar todo el archivo tlog */
        String fileName = "lg260301.711";
        StringDecode.readBinaryFile(fileName, "decodificado.txt", true);
        StringDecode.hexhex();
        /** decoficar una trx exacta especificando en que liena estaa */
         StringDecode.decodeTrxString99(fileName,"trx1.csv",513);
         //StringDecode.decodeTrxString99("lg260301.711","trx2.csv",2465);

        // ControlTrxErr.processtrx();

        // Scanner scanner = new Scanner(System.in);
        // boolean ejecutar = true;
        //
        // while (ejecutar) {
        // System.out.println("\n===== MENU =====");
        // System.out.println("1 - Enviar email");
        // System.out.println("999 - Salir");
        // System.out.print("Seleccione una opción: ");
        //
        // String opcion = scanner.nextLine();
        //
        // switch (opcion) {
        // case "1":
        // mailService.enviarSaludo("sandovalleonel16@gmail.com");
        // break;
        //
        // case "999":
        // ejecutar = false;
        // System.out.println("Saliendo del programa...");
        // break;
        //
        // default:
        // System.out.println("Opción no válida.");
        // }
        // }
        //
        // scanner.close();

    }
}
