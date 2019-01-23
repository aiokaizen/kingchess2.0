package audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioFile_old implements LineListener {

	private File soundFile;
	private AudioInputStream stream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private volatile boolean playing;
	
	public boolean isPlaying() {
		return playing;
	}
	
	public AudioFile_old(String fileName) {
		try {
			this.soundFile = new File(fileName);
			this.stream = AudioSystem.getAudioInputStream(soundFile);
			this.format = stream.getFormat();
			this.info = new DataLine.Info(Clip.class, format);
			this.clip = (Clip) AudioSystem.getLine(info);
			this.clip.addLineListener(this);
			this.clip.open(stream);
			this.gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}
	
	public void play() {
		play(-10);
	}
	
	public void play(float volum) {
		this.gainControl.setValue(volum);
		clip.start();
		playing = true;
	}

	@Override
	public void update(LineEvent e) {
		if(e.getType() == LineEvent.Type.START)
			playing = true;
		else if(e.getType() == LineEvent.Type.STOP) {
			clip.stop();
			clip.flush();
			clip.setFramePosition(0);
			playing = false;
		}
	}
}
