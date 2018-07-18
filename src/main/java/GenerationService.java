import helper.GenerationHelper;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

class GenerationService {

    private static final Logger LOGGER = Logger.getLogger(GenerationService.class);
    private String path;
    private int eventCounter;
    private String resultPath;

    /**
     * @param path         файл со списком точек продаж
     * @param eventCounter количество операций
     * @param resultPath   файл для записи результата
     */
    GenerationService(String path, int eventCounter, String resultPath) {
        this.path = path;
        this.eventCounter = eventCounter;
        this.resultPath = resultPath;
    }

    /**
     * Создает файл "дата-время - офис - номер операции - сумма операции".
     * Офис выбирается рандомно из списка.
     */
    public void generate() {
        List<String> offices = GenerationHelper.readFile(path);

        if (GenerationHelper.deleteData(resultPath)) {
            LOGGER.info("Файл " + resultPath + " будет перезаписан");
        }
        OutputStream stream;
        try {
            stream = new FileOutputStream(resultPath);
        } catch (IOException e) {
            LOGGER.error("Неудачная попытка создания OutputStream: " + e.getMessage());
            throw new RuntimeException(e);
        }
        GenerationHelper.writeLines(offices, stream, eventCounter);
        LOGGER.info("Файл " + Paths.get(resultPath).toAbsolutePath() + " создан. Количество записанных операций: " + eventCounter);
    }


}

