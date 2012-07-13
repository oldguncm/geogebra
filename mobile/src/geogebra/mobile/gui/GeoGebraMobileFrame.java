package geogebra.mobile.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBar;

public class GeoGebraMobileFrame
{

	private RootPanel root;
	private HeaderPanel headerPanel;
	private ButtonBar toolBar;

	public GeoGebraMobileFrame()
	{
		root = RootPanel.get();

		headerPanel = new HeaderPanel();
		toolBar = new ButtonBar();

	}

	public void start()
	{
		// set viewport and other settings for mobile
		MGWT.applySettings(MGWTSettings.getAppSetting());

		headerPanel.addStyleName("headerpanel");

		headerPanel.setTitle("Title");
		headerPanel.setCenter("Title");

		toolBar.addStyleName("toolbar");

		Button[] b = new Button[10];
		for (int i = 0; i < 10; i++)
		{
			b[i] = new Button();
			b[i].setText("bla" + i);
			b[i].addStyleName("toolbutton");
			toolBar.add(b[i]);
		}

		root.add(headerPanel);
		root.add(toolBar);
	}
}
