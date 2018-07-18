
import org.apache.log4j.Logger;


public class Runner {

    private static final Logger LOGGER = Logger.getLogger(Runner.class);

    /**
     * В параметрах запуска передавать:
     * Путь до файла с офисами, количество операций, путь сохранения результата.
     */
    public static void main(String[] args) {

        GenerationService genService;

        if (args.length == 3) {
            try{
                int counter = Integer.parseInt(args[1]);
                genService = new GenerationService(args[0], counter, args[2]);
            }catch (NumberFormatException e){
                LOGGER.error("Ожидается 3 аргумента: файл с адресами, количество генерируемых операций" +
                        "(арабские цифры) и конечный файл");
                throw new IllegalArgumentException(e);
            }

        } else {
            LOGGER.error("Ожидается 3 аргумента: файл с адресами, количество генерируемых операций и конечный файл");
            throw new IllegalArgumentException();
        }

        try {
            genService.generate();
        } catch (Exception e) {
            LOGGER.error("Не удалось создать файл, очень жаль. ", e);
        }

    }
}
