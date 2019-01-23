package audio;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioFile implements LineListener {
	
	private static ArrayList<AudioFile> audioFiles;
	private static int currentSong;

	private File soundFile;
	private AudioInputStream stream;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;
	private volatile boolean playing;
	private boolean loop;

	public static ArrayList<AudioFile> getAudioFiles() {
		return audioFiles;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	static {
		audioFiles = new ArrayList<>();
		currentSong = 0;
	}
	
	public AudioFile(String fileName, boolean loop) {
		try {
			this.soundFile = new File(fileName);
			this.stream = AudioSystem.getAudioInputStream(soundFile);
			this.format = stream.getFormat();
			this.info = new DataLine.Info(Clip.class, format);
			this.clip = (Clip) AudioSystem.getLine(info);
			this.clip.addLineListener(this);
			this.clip.open(stream);
			this.gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			this.loop = loop;
			if(loop)
				audioFiles.add(this);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}
	
	public static void play(AudioFile song) {
		play(song, -10);
	}
	
	public static void play(AudioFile song, float volum) {
		song.gainControl.setValue(volum);
		song.clip.start();
		song.playing = true;
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
			
			if(loop) {
				currentSong = (currentSong >= audioFiles.size()) ? 0 : ++currentSong;
				play(audioFiles.get(currentSong));
			}
		}
	}
}
