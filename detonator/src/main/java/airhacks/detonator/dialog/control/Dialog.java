package airhacks.detonator.dialog.control;

public interface Dialog {

    static boolean proceed(String stackName) {
       var line = System
             .console()
             .readLine("delete %s [Y/y]?: ".formatted(stackName));
       return line.equalsIgnoreCase("y");
    }

    static boolean ask(String question) {
       var line = System
             .console()
             .readLine("%s [Y/y]?: ".formatted(question));
       return line.equalsIgnoreCase("y");
    }    
    
}
