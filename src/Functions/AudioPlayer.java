package Functions;

import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer{

    DataLine.Info dataLineInfo;
    ArrayList<byte[]> audioBytes;
    AudioFormat audioFormat;

    public AudioPlayer(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            audioFormat = audioInputStream.getFormat();
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                System.out.println(audioFormat.getEncoding());
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(), 16, audioFormat.getChannels(),
                    audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
                audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
            }

            // 打开输出设备
            dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,
                AudioSystem.NOT_SPECIFIED);

            int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
            int numBytes = 1024 * bytesPerFrame;
            audioBytes = new ArrayList<>();
            while (true) {
                byte[] cur = new byte[numBytes];
                if (audioInputStream.read(cur) == -1) {
                    break;
                }
                audioBytes.add(cur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        try {
            PlayerThread thread = new PlayerThread((SourceDataLine) AudioSystem.getLine(dataLineInfo));
            thread.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    class PlayerThread extends Thread{
        SourceDataLine sourceDataLine;
        PlayerThread(SourceDataLine sourceDataLine){
            this.sourceDataLine = sourceDataLine;
        }

        @Override
        public void run() {
            try {
                sourceDataLine.open(audioFormat);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            sourceDataLine.start();

            for (byte[] each : audioBytes) {
                sourceDataLine.write(each, 0, each.length);
            }

            sourceDataLine.drain();
            sourceDataLine.stop();
            sourceDataLine.close();
        }
    }
}

