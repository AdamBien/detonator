package airhacks.detonator.log.boundary;

public interface Logger {

    public enum TerminalColors {
    
        INFO("\u001B[34m"), TIME("\033[1;90m"), RESET("\u001B[0m");
    
        private final String value;
    
    
        private TerminalColors(String value) {
            this.value =  value;
        }
    
        public String value() {
            return this.value;
        }
    
    }

    static void info(String info) {
       System.out.println(info);
    }

    static void white(String info) {
       System.out.println(info);
    }
    
}
