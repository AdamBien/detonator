package airhacks.detonator.log.boundary;

public interface Logger {

    static void info(String info) {
       System.out.println(info);
    }
    
}
