import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {

    private Clip clip;

    public Music(final String path) {
        try {
            final AudioInputStream ais =
                    AudioSystem.getAudioInputStream(new File(path));
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.clip.start();
        } catch (final Exception e) {
            System.err.println("Не удалось воспроизвести файл: " + path);
            e.printStackTrace();
        }
    }
}