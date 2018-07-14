
import org.apache.log4j.Logger;

/**
 * В параметрах запуска передавать:
 * Путь до файла с офисами, количество операций, путь сохранения результата.
 */
public class Runner {

    private static final Logger LOGGER = Logger.getLogger(Runner.class);

    public static void main(String[] args) {

        GenerationService genService;

        if (args.length == 0) {
            LOGGER.info("Не задано аргументов, будет использована конфигурация по умолчанию.\n" +
                    "Файл с адресами офисов будет сгенерирован самостоятельно.");
            genService = new GenerationService();
        } else if (args.length == 3) {
            genService = new GenerationService(args[0], Integer.valueOf(args[1]), args[2]);
        } else {
            LOGGER.error("Ожидается 3 аргумента: файл с адресами, количество генерируемых операций и конечный файл");
            throw new IllegalArgumentException();
        }

        try {
            genService.generate();
        } catch (RuntimeException e) {
            LOGGER.error("Не удалось создать файл, очень жаль. ", e);
        }

    }
}
