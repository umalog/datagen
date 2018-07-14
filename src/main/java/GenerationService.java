import helper.GenerationHelper;

class GenerationService {

    private String path;
    private int eventCounter;
    private String resultPath;

    GenerationService() {
        this.path = GenerationHelper.createTestFile();
        this.eventCounter = GenerationHelper.getRandomInt(90000, 100);
        this.resultPath = System.getProperty("user.home") + "/Downloads/result.txt";
    }

    /**
     * @param path файл со списком точек продаж
     * @param eventCounter количество операций
     * @param resultPath файл для записи результата
     */
    GenerationService(String path, int eventCounter, String resultPath) {
        this.path = path;
        this.eventCounter = eventCounter;
        this.resultPath = resultPath;
    }

    /**
     * Создает файл "дата-время - номер точки продаж - номер операции - сумма операции"
     *
     */
    void generate() {
        String[] offices = GenerationHelper.readFile(path);
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= eventCounter; i++) {
            builder.append(GenerationHelper.getDate())
                    .append("__")
                    .append(offices[GenerationHelper.getRandomInt(offices.length)])
                    .append("__")
                    .append(i)
                    .append("__")
                    .append(GenerationHelper.getPrice())
                    .append(System.lineSeparator());
        }
        GenerationHelper.writeData(resultPath, builder.toString());
    }
}