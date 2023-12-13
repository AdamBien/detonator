package airhacks.detonator.dialog.control;

public interface Dialog {

    static boolean proceed(String stackName) {
       var line = System
             .console()
             .readLine("delete %s [Y/y]?: ".formatted(stackName));
       return line.equalsIgnoreCase("y");
    }
    
}
