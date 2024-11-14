package audio;

public class MusicPlayer implements Runnable {
	
	private AudioFile sfx;
	
	public MusicPlayer(boolean loop, String... files) {
		for(String file : files) {
			if(loop) {
				if(!file.contains("move") && !file.contains("catch"))
					new AudioFile("src/assets/audio/soundtrack/" + file + ".wav", loop);
				else
					new AudioFile("src/assets/audio/sfx/" + file + ".wav", loop);
			}
			else {
				sfx = new AudioFile("src/assets/audio/sfx/" + file + ".wav", loop);
			}
		}
	}

	@Override
	public void run() {
		if(sfx != null)
			AudioFile.play(sfx);
		else
			AudioFile.play(AudioFile.getAudioFiles().get(0));
	}

}
