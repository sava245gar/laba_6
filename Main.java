import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String urlString = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1bGp6Fn_QLomsca-m_zrADAYsm2OgTVsqzs-r85L6zA&s=10";
        String downloadDir = "C:\\Программированиее";

        String filePath = downloadDir + File.separator + "downloaded_image.jpg";

        Thread downloadThread = new Thread(() -> {
            System.out.println("Подождите, идет скачивание изображения в: " + filePath);
            try {
                long durationMs = downloadImageFile(filePath, urlString);

                System.out.printf("Скачивание успешно завершено за %d мс%n", durationMs);

            } catch (IOException ex) {
                System.err.println("Ошибка при скачивании: " + ex.getMessage());
            }
        });

        downloadThread.start();
        try {
            downloadThread.join();
        } catch (InterruptedException ex) {
            System.err.println("Скачивание было прервано");
            Thread.currentThread().interrupt();
        }
    }

    public static long downloadImageFile(final String filePath, final String urlString) throws IOException {
        long startTime = System.currentTimeMillis();

        File targetFile = new File(filePath);
        File parentDir = targetFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());
             FileOutputStream fout = new FileOutputStream(targetFile)) {
            final byte[] data = new byte[8192];
            int count;
            while ((count = in.read(data, 0, 8192)) != -1) {
                fout.write(data, 0, count);
            }
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}