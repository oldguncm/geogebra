package org.geogebra.desktop.awt;

import java.awt.Shape;
import java.awt.geom.QuadCurve2D;

import org.geogebra.common.awt.GAffineTransform;
import org.geogebra.common.awt.GPathIterator;
import org.geogebra.common.awt.GQuadCurve2D;
import org.geogebra.common.awt.GRectangle2D;

public class GQuadCurve2DD implements GQuadCurve2D, GShapeD {

	private QuadCurve2D.Double impl;

	public GQuadCurve2DD() {
		impl = new QuadCurve2D.Double();
	}

	public void setCurve(double[] parpoints, int i) {
		impl.setCurve(parpoints, i);
	}

	public boolean contains(double xTry, double yTry) {
		return impl.contains(xTry, yTry);
	}

	public boolean intersects(double x, double y, double lengthX, double lengthY) {
		return impl.intersects(x, y, lengthX, lengthY);
	}

	public boolean intersects(int i, int j, int k, int l) {
		return impl.intersects(i, j, k, l);
	}

	public boolean contains(int x, int y) {
		return impl.contains(x, y);
	}

	public GRectangleD getBounds() {
		return new GRectangleD(impl.getBounds());
	}

	public GRectangle2D getBounds2D() {
		return new GGenericRectangle2DD(impl.getBounds2D());
	}

	public boolean contains(GRectangle2D rectangle) {
		return impl.contains(GRectangleD.getAWTRectangle2D(rectangle));
	}

	public GPathIterator getPathIterator(GAffineTransform affineTransform) {
		return new GPathIteratorD(impl.getPathIterator(
				GAffineTransformD
						.getAwtAffineTransform(affineTransform)));
	}

	public GPathIterator getPathIterator(GAffineTransform at, double flatness) {
		return new GPathIteratorD(impl.getPathIterator(
				GAffineTransformD.getAwtAffineTransform(at),
				flatness));
	}

	public boolean intersects(GRectangle2D r) {
		return impl.intersects(GGenericRectangle2DD
				.getAWTRectangle2D(r));
	}

	public Shape getAwtShape() {
		return impl;
	}

	public void setCurve(double x1, double y1, double ctrlx, double ctrly,
			double x2, double y2) {
		impl.setCurve(x1, y1, ctrlx, ctrly, x2, y2);

	}

}
