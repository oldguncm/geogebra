package geogebra.touch.gui;

import geogebra.common.awt.GColor;
import geogebra.common.kernel.Kernel;
import geogebra.touch.FileManagerM;
import geogebra.touch.TouchApp;
import geogebra.touch.TouchEntryPoint;
import geogebra.touch.controller.TouchController;
import geogebra.touch.gui.algebra.AlgebraViewPanel;
import geogebra.touch.gui.elements.ArrowImageButton;
import geogebra.touch.gui.elements.StandardImageButton;
import geogebra.touch.gui.elements.header.TabletHeaderPanel;
import geogebra.touch.gui.elements.stylingbar.StylingBar;
import geogebra.touch.gui.elements.toolbar.ToolBar;
import geogebra.touch.gui.euclidian.EuclidianViewPanel;
import geogebra.touch.gui.laf.LookAndFeel;
import geogebra.touch.model.TouchModel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Coordinates the GUI of the tablet.
 * 
 */
public class TabletGUI extends HeaderPanel implements GeoGebraTouchGUI
{
	public static final float ALGEBRA_VIEW_WIDTH_FRACTION = 0.2f;
	public static final int FOOTER_BORDER_WIDTH = 1;
	private static final int ALGEBRA_BUTTON_WIDTH = 48;

	List<ResizeListener> resizeListeners = new ArrayList<ResizeListener>();

	private TouchModel touchModel;

	DockLayoutPanel contentPanel;
	private ToolBar toolBar;

	EuclidianViewPanel euclidianViewPanel;
	AlgebraViewPanel algebraViewPanel;
	StylingBar stylingBar;

	private PopupPanel algebraViewButtonPanel;
	private Panel algebraViewArrowPanel;
	StandardImageButton algebraViewButton;

	/**
	 * Sets the viewport and other settings, creates a link element at the end of
	 * the head, appends the css file and initializes the GUI elements.
	 */
	public TabletGUI()
	{
		// required to start the kernel
		this.euclidianViewPanel = new EuclidianViewPanel();
	}

	/**
	 * Creates a new instance of {@link TouchController} and
	 * {@link MobileAlgebraController} and initializes the
	 * {@link EuclidianViewPanel euclidianViewPanel} and {@link AlgebraViewPanel
	 * algebraViewPanel} according to these instances.
	 * 
	 * @param kernel
	 *          Kernel
	 */
	@Override
	public void initComponents(final Kernel kernel, FileManagerM fm)
	{
		this.touchModel = new TouchModel(kernel, this);

		// Initialize GUI Elements
		TouchEntryPoint.getLookAndFeel().buildHeader(this, (TouchApp) kernel.getApplication(), this.touchModel, fm);

		this.contentPanel = new DockLayoutPanel(Unit.PX);

		TouchController ec = new TouchController(this.touchModel, kernel.getApplication());
		ec.setKernel(kernel);

		int width = (int) (Window.getClientWidth() * (1 - ALGEBRA_VIEW_WIDTH_FRACTION));
		int height = Window.getClientHeight() - TouchEntryPoint.getLookAndFeel().getPanelsHeight();
		this.euclidianViewPanel.setPixelSize(width, height);
		this.euclidianViewPanel.initEuclidianView(ec, super.getHeaderWidget(), width, height);

		this.stylingBar = new StylingBar(this.touchModel, this.euclidianViewPanel.getEuclidianView(), this.euclidianViewPanel);
		this.touchModel.getGuiModel().setStylingBar(this.stylingBar);

		this.algebraViewPanel = new AlgebraViewPanel(ec, kernel);

		this.contentPanel.addEast(this.algebraViewPanel, (int) (Window.getClientWidth() * ALGEBRA_VIEW_WIDTH_FRACTION));
		this.contentPanel.add(this.euclidianViewPanel);
		this.contentPanel.setHeight("100%");

		this.euclidianViewPanel.add(this.stylingBar);
		this.euclidianViewPanel.setWidgetPosition(this.stylingBar, 0, 0);

		this.setContentWidget(this.contentPanel);

		this.toolBar = new ToolBar(this.touchModel, (TouchApp) kernel.getApplication(), this);
		this.setFooterWidget(this.toolBar);

		// show/hide AlgebraView Button
		this.algebraViewButtonPanel = new PopupPanel();
		this.algebraViewArrowPanel = new FlowPanel();
		this.algebraViewButton = new ArrowImageButton(getLaf().getIcons().triangle_left());

		this.algebraViewArrowPanel.setStyleName("algebraViewArrowPanel");
		this.algebraViewButton.setStyleName("arrowRight");

		this.algebraViewButtonPanel.setAutoHideEnabled(false);
		this.algebraViewButtonPanel.show();

		// for Win8 position it on top, for others right under appbar
		this.algebraViewButtonPanel.setPopupPosition(width - ALGEBRA_BUTTON_WIDTH, TouchEntryPoint.getLookAndFeel().getAppBarHeight());
		this.algebraViewButtonPanel.setStyleName("algebraViewButtonPanel");

		this.algebraViewButton.addDomHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				TabletGUI.this.toggleAlgebraView();
			}
		}, ClickEvent.getType());

		this.algebraViewButtonPanel.add(this.algebraViewArrowPanel);
		this.algebraViewArrowPanel.add(this.algebraViewButton);

		Window.addResizeHandler(new ResizeHandler()
		{
			@Override
			public void onResize(ResizeEvent event)
			{
				TabletGUI.this.onResize(event);
			}
		});
	}

	private static LookAndFeel getLaf()
  {
	  return TouchEntryPoint.getLookAndFeel();
  }

	public void addResizeListener(ResizeListener rl)
	{
		this.resizeListeners.add(rl);
	}

	public static GColor getBackgroundColor()
	{
		return GColor.LIGHT_GRAY;
	}

	protected void onResize(ResizeEvent event)
	{
		for (ResizeListener res : this.resizeListeners)
		{
			res.onResize(event);
		}

		this.contentPanel.setPixelSize(event.getWidth(), event.getHeight() - getLaf().getPanelsHeight());
		this.contentPanel.onResize();
		updateViewSizes(this.algebraViewPanel.isVisible());

		this.toolBar.setWidth(event.getWidth() + "px");

		this.touchModel.getGuiModel().closeOptions();
	}

	@Override
	public EuclidianViewPanel getEuclidianViewPanel()
	{
		return this.euclidianViewPanel;
	}

	@Override
	public AlgebraViewPanel getAlgebraViewPanel()
	{
		return this.algebraViewPanel;
	}

	void toggleAlgebraView()
	{
		boolean algebraVisible = this.algebraViewPanel.isVisible();
		updateViewSizes(!algebraVisible);
		this.algebraViewPanel.setVisible(!algebraVisible);
	}

	private void updateViewSizes(boolean algebraVisible)
	{
		if (!algebraVisible)
		{
			this.contentPanel.setWidgetSize(this.algebraViewPanel, 0);
			this.euclidianViewPanel.setPixelSize(Window.getClientWidth(), Window.getClientHeight() - TouchEntryPoint.getLookAndFeel().getPanelsHeight());

			// for Win8 position it on top, for others under appbar
			this.algebraViewButtonPanel
			    .setPopupPosition(Window.getClientWidth() - ALGEBRA_BUTTON_WIDTH, TouchEntryPoint.getLookAndFeel().getAppBarHeight());
			this.algebraViewButton.setStyleName("arrowLeft");
			
			// Set algebraviewbutton transparent, when algebra view is closed
			this.algebraViewButtonPanel.addStyleName("transparent");
		}
		else
		{
			this.contentPanel.setWidgetSize(this.algebraViewPanel, Window.getClientWidth() * ALGEBRA_VIEW_WIDTH_FRACTION);

			int euclidianWidth = (int) (Window.getClientWidth() * (1 - ALGEBRA_VIEW_WIDTH_FRACTION));

			this.euclidianViewPanel.setPixelSize(euclidianWidth, Window.getClientHeight() - TouchEntryPoint.getLookAndFeel().getPanelsHeight());

			// for Win8 position it on top, for others under appbar
			this.algebraViewButtonPanel.setPopupPosition(euclidianWidth - ALGEBRA_BUTTON_WIDTH, TouchEntryPoint.getLookAndFeel().getAppBarHeight());
			this.algebraViewButton.setStyleName("arrowRight");
			
			// Set algebraviewbutton nontransparent, when algebra view is open
			this.algebraViewButtonPanel.removeStyleName("transparent");
		}
	}

	@Override
	public void setLabels()
	{
		if (this.algebraViewPanel != null)
		{
			this.algebraViewPanel.setLabels();
		}
		if (TouchEntryPoint.getLookAndFeel() != null) {
			TouchEntryPoint.getLookAndFeel().getTabletHeaderPanel().setLabels();
		}
		this.toolBar.setLabels();
	}

	@Override
	protected void onUnload()
	{
		super.onUnload();
		this.algebraViewButtonPanel.hide();
	}

	@Override
	protected void onLoad()
	{
		super.onLoad();
		if (this.algebraViewButtonPanel != null)
		{
			this.algebraViewButtonPanel.show();
		}
	}

	public TouchModel getTouchModel()
	{
		return this.touchModel;
	}

	public String getConstructionTitle()
	{
		if (this.getHeaderWidget() instanceof TabletHeaderPanel)
		{
			return ((TabletHeaderPanel) this.getHeaderWidget()).getConstructionTitle();
		}
		return "";
	}
	
	public void editTitle() {
		if(this.getHeaderWidget() instanceof TabletHeaderPanel){
			((TabletHeaderPanel)this.getHeaderWidget()).editTitle();
		}
	}

	// TODO: use with SelelctionManager
	// @Override
	// public void updateStylingBar(SelectionManager selectionManager) {
	// this.stylingBar.updateGeos(selectionManager);
	// }
}
