package game;

public class Property {
	
//	private float masterVolume; // Volum property goes from -50 to 0.
	private float musicVolume; // Volum property goes from -50 to 0.
	private float sfxVolum; // Volum property goes from -50 to 0.
	private boolean mute;
	private boolean showAllowedMoves;
	private boolean showLastMove;

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public float getSfxVolum() {
		return sfxVolum;
	}

	public void setSfxVolum(float sfxVolum) {
		this.sfxVolum = sfxVolum;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public boolean isShowAllowedMoves() {
		return showAllowedMoves;
	}

	public void setShowAllowedMoves(boolean showAllowedMoves) {
		this.showAllowedMoves = showAllowedMoves;
	}

	public boolean isShowLastMove() {
		return showLastMove;
	}

	public void setShowLastMove(boolean showLastMove) {
		this.showLastMove = showLastMove;
	}

	public Property() {
		
	}
	
}
