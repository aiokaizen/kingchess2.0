package Theme;

import javafx.scene.paint.Color;

public class Theme {
	
	private static Theme theme;
	
	private static Color lightColor;
	private static Color darkColor;
	
	public static Color getLightColor() {
		return lightColor;
	}
	
	public static Color getDarkColor() {
		return darkColor;
	}
	
	private Theme(String theme) {
		if(theme.equals("classic")) {
			lightColor = Color.valueOf("#FEFDFC");
			darkColor = Color.valueOf("#814D25");
		}
		else if (theme.equals("theme1")) {
			lightColor = Color.valueOf("#eee");
			darkColor = Color.valueOf("#111");
		}
		else if (theme.equals("theme2")) {
			lightColor = Color.valueOf("#eee");
			darkColor = Color.valueOf("#111");
		}
		else if (theme.equals("theme3")) {
			lightColor = Color.valueOf("#eee");
			darkColor = Color.valueOf("#111");
		}
	}
	
	public static Theme initTheme(String theme) {
		if(Theme.theme == null)
			Theme.theme = new Theme(theme);
		
		return Theme.theme;
	}

}
