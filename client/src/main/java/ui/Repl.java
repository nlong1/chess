package ui;

import ui.EscapeSequences;

import java.util.Scanner;

public class Repl {
    private final Client client;
    private boolean loggedIn = false;

    public Repl(String serverUrl) {
        client = new Client(serverUrl);
    }

    public void run(){
        System.out.println(EscapeSequences.WHITE_QUEEN + "CHESS" + EscapeSequences.WHITE_QUEEN + "\ntype help to get started");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            String line = scanner.nextLine();
            try{
                result = client.eval(line,loggedIn);
                if (result == "        logged in"){
                    loggedIn = true;
                }
                else if (result == "        logged out"){
                    loggedIn = false;
                }
                System.out.print(result);
            }
            catch (Throwable e){
                System.out.println(e.toString());
            }
            System.out.println();
        }
    }

}
